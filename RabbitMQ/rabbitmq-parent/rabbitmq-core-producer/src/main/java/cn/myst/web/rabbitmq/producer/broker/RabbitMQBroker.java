package cn.myst.web.rabbitmq.producer.broker;

import cn.myst.web.rabbitmq.api.entity.Message;

/**
 * 具体发送不同种类型消息的接口
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public interface RabbitMQBroker {
    void rapidSend(Message message);

    void confirmSend(Message message);

    void reliantSend(Message message);

    void sendMessages();
}
