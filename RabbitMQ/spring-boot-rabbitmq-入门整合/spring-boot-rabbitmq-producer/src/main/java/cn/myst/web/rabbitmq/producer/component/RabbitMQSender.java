package cn.myst.web.rabbitmq.producer.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author ziming.xing
 * Create Date：2022/4/7
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;


    /**
     * publisher-confirms,实现一个监听器用于监听broker端给我们返回的确认请求：
     * RabbitTemplate.ConfirmCallback
     * <p>
     * publisher-returns，保证消息对broker端是可达的，如果出现路由键不可达的情况，
     * 则使用监听器对不可达的消息进行后续的处理，保证消息的路由成功：
     * RabbitTemplate.ReturnCallback
     * <p>
     * 注意：在发送消息的时候对template配置mandatory=true保证ReturnCallback监听有效
     * 生产端还可以配置其他属性，比如发送重试，超时时间、次数、间隔等。
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        /**
         *
         * @param correlationData 作为一个唯一的标识
         * @param ack broker是否落盘成功
         * @param cause 失败的异常信息
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.error("CorrelationData：{}，ACK：{}，异常信息：{}", correlationData, ack, cause);
        }
    };

    /**
     * 对外发送消息
     *
     * @param message    具体的消息内容
     * @param properties 额外的附加配置
     */
    public void send(Object message, Map<String, Object> properties) {
        MessageHeaders messageHeaders = new MessageHeaders(properties);
        Message<Object> msg = MessageBuilder.createMessage(message, messageHeaders);

        rabbitTemplate.setConfirmCallback(confirmCallback);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
                log.error("Post To Do：" + message);
                return message;
            }
        };

        rabbitTemplate.convertAndSend("exchange-1",
                "springboot.rabbit",
                msg,
                messagePostProcessor,
                correlationData);
    }

}
