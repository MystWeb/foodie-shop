package cn.myst.web.kafka.consumer.service.impl;

import cn.myst.web.kafka.consumer.service.KafkaConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author ziming.xing
 * Create Date：2022/4/18
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    @KafkaListener(groupId = "group02", topics = "topic02")
    @Override
    public void onMessage(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        log.info("消费端接收消息: {}", record.value());
        //	手工签收机制
        acknowledgment.acknowledge();
    }
}
