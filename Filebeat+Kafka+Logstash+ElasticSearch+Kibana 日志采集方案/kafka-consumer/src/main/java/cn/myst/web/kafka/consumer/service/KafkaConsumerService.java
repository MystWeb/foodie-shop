package cn.myst.web.kafka.consumer.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

/**
 * @author ziming.xing
 * Create Date：2022/4/18
 */
public interface KafkaConsumerService {
    /**
     * 监听消息
     *
     * @param record         单条记录消费
     * @param acknowledgment ACK设置
     * @param consumer       Consumer设置
     */
    void onMessage(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer);
}
