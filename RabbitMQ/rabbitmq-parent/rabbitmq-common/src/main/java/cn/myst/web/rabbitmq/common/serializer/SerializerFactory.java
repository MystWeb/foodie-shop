package cn.myst.web.rabbitmq.common.serializer;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public interface SerializerFactory {
    Serializer create();
}
