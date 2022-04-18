package com.example.rabbitmq.tests;

import cn.myst.web.rabbitmq.api.constant.MessageType;
import cn.myst.web.rabbitmq.api.entity.Message;
import cn.myst.web.rabbitmq.producer.broker.impl.ProducerClient;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest
class RabbitmqTestsApplicationTests {

    private final ProducerClient producerClient;

    @Test
    public void testProducerClient() throws Exception {

        for (int i = 0; i < 1; i++) {
            String uniqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", "18");
            Message message = new Message(
                    uniqueId,
//                    "exchange-1",
                    "exchange-2",
                    "springboot.abc",
                    attributes,
                    0);
            message.setMessageType(MessageType.RELIANT);
//			message.setDelayMills(15000);
            producerClient.send(message);
        }

        Thread.sleep(100000);
    }

    @Test
    public void testProducerClient2() throws Exception {

        for (int i = 0; i < 1; i++) {
            String uniqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", "18");
            Message message = new Message(
                    uniqueId,
                    "delayed-exchange",
                    "delayed.abc",
                    attributes,
                    15000);
            message.setMessageType(MessageType.RELIANT);
            producerClient.send(message);
        }

        Thread.sleep(100000);
    }

}
