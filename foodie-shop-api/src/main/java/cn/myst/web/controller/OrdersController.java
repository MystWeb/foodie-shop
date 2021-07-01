package cn.myst.web.controller;

import cn.myst.web.enums.EnumBase;
import cn.myst.web.enums.EnumOrder;
import cn.myst.web.enums.EnumPayMethod;
import cn.myst.web.pojo.bo.SubmitOrderBO;
import cn.myst.web.service.OrderService;
import cn.myst.web.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 */
@Slf4j
@Api(value = "订单", tags = "订单的相关接口")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrdersController extends BaseController {
    private final OrderService orderService;

    @ApiOperation(value = "用户下单", notes = "用户提交订单")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        if (Objects.isNull(submitOrderBO)) {
            return IMOOCJSONResult.errorMsg(EnumBase.PARAMETER_CANNOT_BE_EMPTY.zh);
        }
        // 判断支付方式
        if (Objects.equals(submitOrderBO.getPayMethod(), EnumPayMethod.WE_CHAT.type) &&
                Objects.equals(submitOrderBO.getPayMethod(), EnumPayMethod.ALI_PAY.type)) {
            return IMOOCJSONResult.errorMsg(EnumOrder.PAYMENT_METHOD_IS_NOT_SUPPORTED.zh);
        }
        // 1. 创建订单
        String orderId = orderService.createOrder(submitOrderBO);
        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品

         /*
         1001
         2002 -> 用户购买
         3003 -> 用户购买
         4004
         */
        // TODO 整合redis之后，完善购物车中已结算商品清除，并且同步到前端的cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据

        return IMOOCJSONResult.ok(orderId);
    }

}
