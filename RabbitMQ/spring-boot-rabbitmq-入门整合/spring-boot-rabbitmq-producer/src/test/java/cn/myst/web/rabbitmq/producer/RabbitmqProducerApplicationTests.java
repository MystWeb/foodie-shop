package cn.myst.web.rabbitmq.producer;

import cn.myst.web.rabbitmq.producer.component.RabbitMQSender;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest
class RabbitmqProducerApplicationTests {

    private final RabbitMQSender rabbitMQSender;

    @Test
    void contextLoads() {
    }

    @Test
    void testSender() throws InterruptedException {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("attr1", 12345);
        properties.put("attr2", "A B C D E");
        rabbitMQSender.send("hello rabbitmq!", properties);

        Thread.sleep(10000);
    }

}
