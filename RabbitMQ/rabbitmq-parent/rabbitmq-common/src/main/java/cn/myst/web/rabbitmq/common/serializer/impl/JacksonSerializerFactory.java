package cn.myst.web.rabbitmq.common.serializer.impl;

import cn.myst.web.rabbitmq.api.entity.Message;
import cn.myst.web.rabbitmq.common.serializer.Serializer;
import cn.myst.web.rabbitmq.common.serializer.SerializerFactory;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public class JacksonSerializerFactory implements SerializerFactory {

    private JacksonSerializerFactory() {
    }

    // 单例模式—饥饿加载/饿汉模式
    public static final SerializerFactory INSTANCE = new JacksonSerializerFactory();

    @Override
    public Serializer create() {
        return JacksonSerializer.createParametricType(Message.class);
    }

    // 单例模式—《Effective Java》枚举Enum来实现单例模式
    // 单元素的枚举类型已经成为实现Singleton的最佳方法。
    public static JacksonSerializerFactory getInstance() {
        return JacksonSerializerFactoryEnum.SINGLETON.instance;
    }

    private enum JacksonSerializerFactoryEnum {
        // 枚举项，每个枚举项只会创建一个
        SINGLETON;
        private final JacksonSerializerFactory instance;

        JacksonSerializerFactoryEnum() {
            instance = new JacksonSerializerFactory();
        }
    }
}
