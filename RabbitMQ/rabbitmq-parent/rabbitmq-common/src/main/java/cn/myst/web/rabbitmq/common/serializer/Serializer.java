package cn.myst.web.rabbitmq.common.serializer;

/**
 * 序列化和反序列化的接口
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public interface Serializer {
    byte[] serializeRaw(Object data);

    String serialize(Object data);

    <T> T deserialize(String content);

    <T> T deserialize(byte[] content);
}
