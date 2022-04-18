package cn.myst.web.rabbitmq.api.entity;

import cn.myst.web.rabbitmq.api.enums.EnumMessageException;
import cn.myst.web.rabbitmq.api.enums.EnumMessageType;
import cn.myst.web.rabbitmq.api.exception.MessageRunTimeException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * $MessageBuilder 建造者模式
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
//@Builder
public class MessageBuilder implements Serializable {

    /**
     * 消息的唯一ID
     */
    private String messageId;

    /**
     * 消息的主题
     */
    private String topic;

    /**
     * 消息的路由规则
     */
    private String routingKey = "";

    /**
     * 消息的附加属性
     */
    private Map<String, Object> attributes = new HashMap<>();

    /**
     * 延迟消息的参数配置
     */
    private int delayMills;

    /**
     * 消息类型：默认为confirm消息类型
     */
    private String messageType = EnumMessageType.CONFIRM.getType();

    private static final long serialVersionUID = -7527585233840194621L;

    private MessageBuilder() {
    }

    public static MessageBuilder create() {
        return new MessageBuilder();
    }

    public MessageBuilder withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder withTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder withRoutingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public MessageBuilder withAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public MessageBuilder withAttribute(String key, Object value) {
        this.attributes.put(key, value);
        return this;
    }

    public MessageBuilder withDelayMills(int delayMills) {
        this.delayMills = delayMills;
        return this;
    }

    public MessageBuilder withMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public MessageBuilder withMessageType(EnumMessageType enumMessageType) {
        this.messageType = enumMessageType.getType();
        return this;
    }

    public Message build() {

        // 1. check messageId
        if (Objects.isNull(messageType)) {
            messageId = UUID.randomUUID().toString();
        }
        // 2. topic is null
        if (Objects.isNull(topic)) {
            throw new MessageRunTimeException(EnumMessageException.THIS_TOPIC_IS_NULL.getEn());
        }

        return new Message(messageId, topic, routingKey, attributes, delayMills, messageType);
    }
}
