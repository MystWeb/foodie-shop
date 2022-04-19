package cn.myst.web.kafka.producer.service;

/**
 * @author ziming.xing
 * Create Date：2022/4/18
 */
public interface KafkaProducerService {
    void sendMessage(String topic, Object object);
}
