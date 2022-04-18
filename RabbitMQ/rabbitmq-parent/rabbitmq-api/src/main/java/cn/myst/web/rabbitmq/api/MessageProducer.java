package cn.myst.web.rabbitmq.api;

import cn.myst.web.rabbitmq.api.exception.MessageRunTimeException;
import cn.myst.web.rabbitmq.api.entity.Message;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public interface MessageProducer {

    /**
     * 消息的发送
     *
     * @param message 消息
     * @throws MessageRunTimeException 自定义消息运行时异常
     */
    void send(Message message) throws MessageRunTimeException;

    /**
     * 消息的发送 附带SendCallback回调执行相应的业务逻辑处理
     *
     * @param message 消息
     * @throws MessageRunTimeException 自定义消息运行时异常
     */
    void send(Message message, SendCallback callback) throws MessageRunTimeException;

    /**
     * 消息的批量发送
     *
     * @param messages 消息
     * @throws MessageRunTimeException 自定义消息运行时异常
     */
    void send(List<Message> messages) throws MessageRunTimeException;
}
