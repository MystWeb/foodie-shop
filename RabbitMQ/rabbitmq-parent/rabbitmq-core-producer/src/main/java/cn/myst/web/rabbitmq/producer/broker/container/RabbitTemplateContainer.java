package cn.myst.web.rabbitmq.producer.broker.container;

import cn.myst.web.rabbitmq.api.constant.MessageType;
import cn.myst.web.rabbitmq.api.entity.Message;
import cn.myst.web.rabbitmq.api.enums.EnumMessageType;
import cn.myst.web.rabbitmq.api.exception.MessageRunTimeException;
import cn.myst.web.rabbitmq.common.converter.GenericMessageConverter;
import cn.myst.web.rabbitmq.common.converter.RabbitMessageConverter;
import cn.myst.web.rabbitmq.common.serializer.Serializer;
import cn.myst.web.rabbitmq.common.serializer.SerializerFactory;
import cn.myst.web.rabbitmq.common.serializer.impl.JacksonSerializerFactory;
import cn.myst.web.rabbitmq.producer.service.MessageStoreService;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * RabbitTemplate池化封裝
 * 每一个topic 对应一个 RabbitTemplate
 * 1、提高发送的效率
 * 2、可以根据不同的需求制定化不同的RabbitTemplate
 * 3、RoutingKey rabbit.* 改为spring.*或者其他RoutingKey，每次重新发送的时候都需要对路由规则做一套配置（非常麻烦），这也是池化的其中一个原因
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback {

    private final Map<String/* Topic */, RabbitTemplate> rabbitmqMap = Maps.newConcurrentMap();

    private final ConnectionFactory connectionFactory;

    private final Splitter splitter = Splitter.on("#");

    private final SerializerFactory serializerFactory = JacksonSerializerFactory.getInstance();

    private final MessageStoreService messageStoreService;

    public RabbitTemplate getRabbitTemplate(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        RabbitTemplate rabbitTemplate = rabbitmqMap.get(topic);
        if (Objects.nonNull(rabbitTemplate)) {
            return rabbitTemplate;
        }
        log.info("#RabbitTemplateContainer.getTemplate# topic: {} is not exists, create one", topic);

        RabbitTemplate newRabbitTemplate = new RabbitTemplate(connectionFactory);
        newRabbitTemplate.setExchange(topic);
        newRabbitTemplate.setRoutingKey(message.getRoutingKey());
        newRabbitTemplate.setRetryTemplate(new RetryTemplate());

        // 添加序列化、反序列化和Converter对象
        Serializer serializer = serializerFactory.create();
        GenericMessageConverter genericMessageConverter = new GenericMessageConverter(serializer);
        RabbitMessageConverter rabbitMessageConverter = new RabbitMessageConverter(genericMessageConverter);
        newRabbitTemplate.setMessageConverter(rabbitMessageConverter);

        String messageType = message.getMessageType();
        if (!EnumMessageType.RAPID.getType().equals(messageType)) {
            newRabbitTemplate.setConfirmCallback(this);
            /* 生产环境不建议使用事务，效率非常慢 */
//            newRabbitTemplate.setChannelTransacted(true);
        }
        rabbitmqMap.putIfAbsent(topic, newRabbitTemplate);

        return rabbitmqMap.get(topic);
    }

    /**
     * 无论是 confirm 消息 还是 reliant 消息 ，发送消息以后 broker都会去回调confirm
     *
     * @param correlationData CorrelationData
     * @param ack             确认
     * @param cause           异常
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // 具体的消息应答
        Preconditions.checkNotNull(correlationData);
        List<String> strings = splitter.splitToList(correlationData.getId());

        if (CollectionUtils.isEmpty(strings)) {
            return;
        }
        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));
        String messageType = strings.get(2);

        if (ack) {
            // 当Broker 返回ACK成功时，就是更新一下日志表里对应的消息发送状态为 SEND_OK

            // 	如果当前消息类型为reliant 我们就去数据库查找并进行更新
            if (MessageType.RELIANT.endsWith(messageType)) {
                messageStoreService.success(messageId);
            }
            log.info("send message is OK, confirm messageId：{}, sendTime：{}", messageId, sendTime);
        } else {
            log.error("send message is Fail, confirm messageId：{}, sendTime：{}", messageId, sendTime);
        }
    }
}
