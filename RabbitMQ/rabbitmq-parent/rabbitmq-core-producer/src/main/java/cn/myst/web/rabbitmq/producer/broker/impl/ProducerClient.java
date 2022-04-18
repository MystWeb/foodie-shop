package cn.myst.web.rabbitmq.producer.broker.impl;

import cn.myst.web.rabbitmq.api.MessageProducer;
import cn.myst.web.rabbitmq.api.SendCallback;
import cn.myst.web.rabbitmq.api.constant.MessageType;
import cn.myst.web.rabbitmq.api.entity.Message;
import cn.myst.web.rabbitmq.api.enums.EnumMessageType;
import cn.myst.web.rabbitmq.api.exception.MessageRunTimeException;
import cn.myst.web.rabbitmq.producer.broker.RabbitMQBroker;
import cn.myst.web.rabbitmq.producer.broker.container.MessageHolder;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 发送消息的实际实现类
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProducerClient implements MessageProducer {
    private final RabbitMQBroker rabbitMQBroker;

    @Override
    public void send(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message.getTopic());
        String messageType = message.getMessageType();
        switch (messageType) {
            case MessageType.RAPID:
                rabbitMQBroker.rapidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitMQBroker.confirmSend(message);
                break;
            case MessageType.RELIANT:
                rabbitMQBroker.reliantSend(message);
                break;
            default:
                break;
        }
    }

    @Override
    public void send(Message message, SendCallback callback) throws MessageRunTimeException {

    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {
        messages.forEach(message -> {
            message.setMessageType(EnumMessageType.RAPID.getType());
            MessageHolder.add(message);
        });
        rabbitMQBroker.sendMessages();
    }
}
