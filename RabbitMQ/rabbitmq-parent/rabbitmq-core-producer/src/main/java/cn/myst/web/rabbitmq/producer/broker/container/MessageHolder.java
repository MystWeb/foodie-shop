package cn.myst.web.rabbitmq.producer.broker.container;

import cn.myst.web.rabbitmq.api.entity.Message;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public class MessageHolder {
    private final List<Message> messages = Lists.newArrayList();

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static final ThreadLocal<MessageHolder> holder = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new MessageHolder();
        }
    };

    public static void add(Message message) {
        if (Objects.isNull(message)){
            return;
        }
        holder.get().messages.add(message);
    }

    public static List<Message> clear() {
        List<Message> tmp = Lists.newArrayList(holder.get().messages);
        holder.remove();
        return tmp;
    }
}
