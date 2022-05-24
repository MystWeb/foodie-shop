package cn.myst.web.tcc.demo.controller;

import cn.myst.web.tcc.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PaymentController {
    private final PaymentService paymentService;

    @RequestMapping("payment")
    public String payment(int userId, int orderId, BigDecimal amount) throws Exception {
        int result = paymentService.paymentMQ(userId, orderId, amount);
        return "支付结果：" + result;
    }
}