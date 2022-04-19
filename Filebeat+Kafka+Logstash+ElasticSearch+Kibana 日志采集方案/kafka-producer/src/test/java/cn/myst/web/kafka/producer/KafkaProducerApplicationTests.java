package cn.myst.web.kafka.producer;

import cn.myst.web.kafka.producer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest
class KafkaProducerApplicationTests {

    private final KafkaProducerService kafkaProducerService;

    @Test
    public void send() throws InterruptedException {

        String topic = "topic02";
        for (int i = 0; i < 1000; i++) {
            kafkaProducerService.sendMessage(topic, "hello kafka" + i);
            Thread.sleep(50);
        }

        Thread.sleep(Integer.MAX_VALUE);

    }

}
