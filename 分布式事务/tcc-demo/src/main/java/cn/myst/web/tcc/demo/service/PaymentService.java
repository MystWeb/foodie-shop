package cn.myst.web.tcc.demo.service;

import cn.myst.web.tcc.demo.domain.db131.Account1;
import cn.myst.web.tcc.demo.domain.db131.PaymentMsg;
import cn.myst.web.tcc.demo.mapper.db131.Account1Mapper;
import cn.myst.web.tcc.demo.mapper.db131.PaymentMsgMapper;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PaymentService {
    private final Account1Mapper accountAMapper;
    private final PaymentMsgMapper paymentMsgMapper;
    private final DefaultMQProducer producer;

    /**
     * 支付接口
     *
     * @return 0:成功；1:用户不存在;2:余额不足
     */
    @Transactional(transactionManager = "tm131")
    public int payment(int userId, int orderId, BigDecimal amount) {
        //支付操作
        Account1 account1 = accountAMapper.selectById(userId);
        if (account1 == null) return 1;
        if (account1.getBalance().compareTo(amount) < 0) return 2;
        account1.setBalance(account1.getBalance().subtract(amount));
        accountAMapper.updateById(account1);

        PaymentMsg paymentMsg = new PaymentMsg();
        paymentMsg.setOrderId(orderId);
        paymentMsg.setStatus(0);//未发送
        paymentMsg.setFailureCount(0);//失败次数
        paymentMsg.setCreateTime(new Date());
        paymentMsg.setCreateUser(userId);
        paymentMsg.setUpdateTime(new Date());
        paymentMsg.setUpdateUser(userId);

        paymentMsgMapper.insert(paymentMsg);

        return 0;
    }

    /**
     * 支付接口(消息队列)
     *
     * @return 0:成功；1:用户不存在;2:余额不足
     */
    @Transactional(transactionManager = "tm131", rollbackFor = Exception.class)
    public int paymentMQ(int userId, int orderId, BigDecimal amount) throws Exception {
        //支付操作
        Account1 account1 = accountAMapper.selectById(userId);
        if (account1 == null) return 1;
        if (account1.getBalance().compareTo(amount) < 0) return 2;
        account1.setBalance(account1.getBalance().subtract(amount));
        accountAMapper.updateById(account1);

        Message message = new Message();
        message.setTopic("payment");
        message.setKeys(orderId + "");
        message.setBody("订单已支付".getBytes());

        try {
            SendResult result = producer.send(message);
            if (result.getSendStatus() == SendStatus.SEND_OK) {
                return 0;
            } else {
                throw new Exception("消息发送失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}