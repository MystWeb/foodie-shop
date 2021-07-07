package cn.myst.web.controller;

import org.springframework.stereotype.Controller;

/**
 * @author ziming.xing
 * Create Date：2021/6/20
 */
@Controller
public class BaseController {

    public static final Integer PAGE = 1;
    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    public static final String FOODIE_SHOPCART = "shopcart";

    // 支付中心的调用地址
    public static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";        // produce
    public static final String PAYMENT_CENTER_ORDER_INFO_URL = "http://payment.t.mukewang.com/foodie-payment/payment/getPaymentCenterOrderInfo";        // produce

    // 微信支付成功 -> 支付中心 -> 天天吃货平台
    //                       |-> 回调通知的url
    public static final String PAY_RETURN_URL = "http://api.z.mukewang.com/foodie-dev-api/orders/notifyMerchantOrderPaid";
//    public static final String PAY_RETURN_URL = "http://localhost:8088/orders/notifyMerchantOrderPaid";
}
