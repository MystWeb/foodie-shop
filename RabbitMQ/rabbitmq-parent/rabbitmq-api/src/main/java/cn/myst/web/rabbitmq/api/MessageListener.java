package cn.myst.web.rabbitmq.api;

import cn.myst.web.rabbitmq.api.entity.Message;

/**
 * 消费者监听消息
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public interface MessageListener {

    /**
     * 监听消息
     *
     * @param message 消息
     */
    void onMessage(Message message);
}
