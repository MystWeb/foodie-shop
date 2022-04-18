package cn.myst.web.rabbitmq.common.converter;

import cn.myst.web.rabbitmq.common.serializer.Serializer;
import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * 序列化和反序列化—转换AMQP的Message为Java Message
 *
 * @author ziming.xing
 * Create Date：2022/4/12
 */
public class GenericMessageConverter implements MessageConverter {

    private final Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);
        this.serializer = serializer;
    }

    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(this.serializer.serializeRaw(o), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return this.serializer.deserialize(message.getBody());
    }
}
