package cn.myst.web.rabbitmq.api.entity;

import cn.myst.web.rabbitmq.api.enums.EnumMessageException;
import cn.myst.web.rabbitmq.api.enums.EnumMessageType;
import cn.myst.web.rabbitmq.api.exception.MessageRunTimeException;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
@Data
public class Message implements Serializable {

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

    private static final long serialVersionUID = 5392542422316153257L;

    public Message() {
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills) {
        // 1. check messageId
        if (Objects.isNull(messageType)) {
            messageId = UUID.randomUUID().toString();
        }
        // 2. topic is null
        if (Objects.isNull(topic)) {
            throw new MessageRunTimeException(EnumMessageException.THIS_TOPIC_IS_NULL.getEn());
        }

        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills, String messageType) {

        // 1. check messageId
        if (Objects.isNull(messageType)) {
            messageId = UUID.randomUUID().toString();
        }
        // 2. topic is null
        if (Objects.isNull(topic)) {
            throw new MessageRunTimeException(EnumMessageException.THIS_TOPIC_IS_NULL.getEn());
        }

        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
        this.messageType = messageType;
    }
}
