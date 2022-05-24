package cn.myst.web.tcc.demo.consumer;

import cn.myst.web.tcc.demo.domain.db132.Order;
import cn.myst.web.tcc.demo.mapper.db132.OrderMapper;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
import static org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus.RECONSUME_LATER;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Component("messageListener")
public class ChangeOrderStatus implements MessageListenerConcurrently {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (list == null || list.size() == 0) return CONSUME_SUCCESS;

        for (MessageExt messageExt : list) {
            String orderId = messageExt.getKeys();
            String msg = new String(messageExt.getBody());
            System.out.println("msg=" + msg);
            Order order = orderMapper.selectById(Integer.parseInt(orderId));

            if (order == null) return RECONSUME_LATER;
            try {
                order.setOrderStatus(1); // 已支付
                order.setUpdateTime(new Date());
                order.setUpdateUser(0); // 系统更新
                orderMapper.updateById(order);
            } catch (Exception e) {
                e.printStackTrace();
                return RECONSUME_LATER;
            }
        }

        return CONSUME_SUCCESS;
    }
}
