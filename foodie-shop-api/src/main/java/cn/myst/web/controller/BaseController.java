package cn.myst.web.controller;

import org.springframework.stereotype.Controller;

import java.io.File;

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
    // 由于支付中心模块部署在外网服务器，此时本地启动项目处于内网状态，支付中心回调通知无法访问到你的内网IP地址
    // 方式1：通过内网穿透工具（例）：https://natapp.cn/，将你本地电脑发布到互联网，可以在互联网任何服务器、第三方系统都可以访问你本地电脑的某一个接口
    // 方式2：部署项目至外网服务器
//    public static final String PAY_RETURN_URL = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    // 用户头像上传的位置
    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "workspaces" +
            File.separator + "images" +
            File.separator + "foodie" +
            File.separator + "faces";
}
