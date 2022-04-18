package cn.myst.web.rabbitmq.producer.broker.impl;

import cn.myst.web.rabbitmq.api.entity.Message;
import cn.myst.web.rabbitmq.api.enums.EnumMessageType;
import cn.myst.web.rabbitmq.producer.broker.RabbitMQBroker;
import cn.myst.web.rabbitmq.producer.broker.container.AsyncBaseQueue;
import cn.myst.web.rabbitmq.producer.broker.container.MessageHolder;
import cn.myst.web.rabbitmq.producer.broker.container.MessageHolderAyncQueue;
import cn.myst.web.rabbitmq.producer.broker.container.RabbitTemplateContainer;
import cn.myst.web.rabbitmq.producer.constant.BrokerMessageConst;
import cn.myst.web.rabbitmq.producer.entity.BrokerMessage;
import cn.myst.web.rabbitmq.producer.enums.EnumBrokerMessageStatus;
import cn.myst.web.rabbitmq.producer.service.MessageStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RabbitMQBrokerImpl implements RabbitMQBroker {

    private final RabbitTemplateContainer rabbitTemplateContainer;

    private final MessageStoreService messageStoreService;

    /**
     * 发送消息的核心方法（不对外暴漏），使用异步线程池进行发送消息
     *
     * @param message 消息
     */
    private void sendKernel(Message message) {
        if (Objects.isNull(message)) {
            return;
        }
        AsyncBaseQueue.submit(() -> {
            MessageConvertAndSend(message);
            log.info("#RabbitMQBrokerImpl.sendKernel# send to rabbitmq messageId：{}", message.getMessageId());
        });
    }

    @Override
    public void rapidSend(Message message) {
        if (Objects.isNull(message)) {
            return;
        }
        message.setMessageType(EnumMessageType.RAPID.getType());
        this.sendKernel(message);
    }

    @Override
    public void confirmSend(Message message) {
        if (Objects.isNull(message)) {
            return;
        }
        message.setMessageType(EnumMessageType.CONFIRM.getType());
        this.sendKernel(message);
    }

    @Override
    public void reliantSend(Message message) {
        if (Objects.isNull(message)) {
            return;
        }
        message.setMessageType(EnumMessageType.RELIANT.getType());
        String messageId = message.getMessageId();
        BrokerMessage bm = messageStoreService.selectByMessageId(messageId);

        // 1、如果消息不存在数据库，则把数据库的消息发送日志先记录好
        if (Objects.isNull(bm)) {
            Date now = Date.from(Instant.now());
            BrokerMessage brokerMessage = new BrokerMessage();
            brokerMessage.setMessageId(messageId);
            brokerMessage.setStatus(EnumBrokerMessageStatus.SENDING.getCode());
            brokerMessage.setMessage(message);
            // brokerMessage.setTryCount(); 在最开始发送的时候不需要进行设置
            brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
            brokerMessage.setCreateTime(now);
            brokerMessage.setUpdateTime(now);
            messageStoreService.insert(brokerMessage);
        }

        // 2、执行真正地发送消息逻辑
        this.sendKernel(message);
    }

    @Override
    public void sendMessages() {
        List<Message> messages = MessageHolder.clear();
        if (CollectionUtils.isEmpty(messages)) {
            return;
        }
        messages.forEach(message -> MessageHolderAyncQueue.submit(() -> {
            MessageConvertAndSend(message);
            log.info("#RabbitMQBrokerImpl.sendMessages# send to rabbitmq messageId：{}", message.getMessageId());
        }));
    }

    /**
     * 消息转换并发送
     *
     * @param message 消息
     */
    private void MessageConvertAndSend(Message message) {
        String messageId = message.getMessageId();
        String topic = message.getTopic();
        String routingKey = message.getRoutingKey();
        CorrelationData correlationData = new CorrelationData(String.format("%s#%s#%s",
                messageId,
                Instant.now().getEpochSecond(),
                message.getMessageType()));
        RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getRabbitTemplate(message);
        rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
    }
}
