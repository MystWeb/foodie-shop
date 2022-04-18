package cn.myst.web.rabbitmq.common.converter;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * 静态代理/Java 设计模式之装饰者模式
 * 装饰者模式指的是在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。它是通过创建一个包装对象，也就是装饰者来包裹真实的对象。
 * 所以装饰者可以动态地将责任附加到对象上。若要扩展功能，装饰者提供了比继承更有弹性的方案。
 *
 * @author ziming.xing
 * Create Date：2022/4/12
 */
public class RabbitMessageConverter implements MessageConverter {

    private final GenericMessageConverter delegate;

    // 过期时间：24小时
    public final String expiration = String.valueOf(24 * 60 * 60 * 1000);

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }

    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
//        messageProperties.setExpiration(expiration);
        // RabbitMQ延迟插件发送延迟消息
        if (o instanceof cn.myst.web.rabbitmq.api.entity.Message) {
            cn.myst.web.rabbitmq.api.entity.Message message = (cn.myst.web.rabbitmq.api.entity.Message) o;
            messageProperties.setDelay(message.getDelayMills());
        }
        return this.delegate.toMessage(o, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        cn.myst.web.rabbitmq.api.entity.Message javaMessage = (cn.myst.web.rabbitmq.api.entity.Message) this.delegate.fromMessage(message);
        return javaMessage;
    }
}
