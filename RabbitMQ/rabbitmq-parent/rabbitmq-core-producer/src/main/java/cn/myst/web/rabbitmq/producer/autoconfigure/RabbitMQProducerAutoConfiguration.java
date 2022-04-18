package cn.myst.web.rabbitmq.producer.autoconfigure;

import cn.myst.web.rabbitmq.task.annotation.EnableElasticJob;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
@EnableElasticJob
@Configuration
@ComponentScan({"cn.myst.web.rabbitmq.producer.*"})
public class RabbitMQProducerAutoConfiguration {

}
