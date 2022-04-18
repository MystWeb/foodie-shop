package cn.myst.web.rabbitmq.consumer.component;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2022/4/7
 */
@Slf4j
@Component
public class RabbitMQReceive {

    /**
     * 组合使用监听注解：@RabbitListener @QueueBinding @Queue @Exchange
     *
     * @param message 具体的消息内容
     * @param channel 通道
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1", durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(name = "exchange-1",
                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                    type = "topic",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "springboot.*")
    )
    @RabbitHandler
    public void onMessage(Message<Object> message, Channel channel) throws IOException {
        // 1. 收到消息以后进行业务端消费处理
        log.error("消费消息：{}", message.getPayload());

        // 2. 处理成功之后，获取deliveryTag，并进行手工的ACK操作
        // 因为配置文件的配置是“手工签收”：spring.rabbitmq.listener.simple.acknowledge-mode=manual
        Object messageHeader = message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        if (Objects.isNull(messageHeader)) {
            return;
        }
        long deliveryTag = Long.parseLong(messageHeader.toString());
        channel.basicAck(deliveryTag, false);
    }
}