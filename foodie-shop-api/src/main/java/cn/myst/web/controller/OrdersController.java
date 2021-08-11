package cn.myst.web.controller;

import cn.myst.web.enums.*;
import cn.myst.web.exception.BusinessException;
import cn.myst.web.pojo.OrderStatus;
import cn.myst.web.pojo.bo.ShopcartBO;
import cn.myst.web.pojo.bo.SubmitOrderBO;
import cn.myst.web.pojo.vo.MerchantOrdersVO;
import cn.myst.web.pojo.vo.OrderVO;
import cn.myst.web.service.OrderService;
import cn.myst.web.utils.CookieUtils;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.JsonUtils;
import cn.myst.web.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 */
@Slf4j
@Api(value = "订单", tags = "订单的相关接口")
@RestController
@RequestMapping("orders")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrdersController extends BaseController {
    private final OrderService orderService;
    private final RestTemplate restTemplate;
    private final RedisOperator redisOperator;

    @ApiOperation(value = "用户下单", notes = "用户提交订单")
    @PostMapping("create")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        if (Objects.isNull(submitOrderBO)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 判断支付方式
        if (Objects.equals(submitOrderBO.getPayMethod(), EnumPayMethod.WE_CHAT.type) &&
                Objects.equals(submitOrderBO.getPayMethod(), EnumPayMethod.ALI_PAY.type)) {
            return IMOOCJSONResult.errorMsg(EnumOrder.PAYMENT_METHOD_IS_NOT_SUPPORTED.zh);
        }
        // 0. 判断购物车数据是否正常
        String shopCartKey = EnumRedisKeys.SHOP_CART.key + ":" + submitOrderBO.getUserId();
        String json = redisOperator.get(shopCartKey);
        if (StringUtils.isBlank(json)) {
            return IMOOCJSONResult.errorMsg(EnumOrder.SHOPPING_CART_DATA_ERROR.zh);
        }
        // redis中已经有购物车了
        List<ShopcartBO> shopCartList = JsonUtils.jsonToList(json, ShopcartBO.class);
        // 1. 创建订单
        OrderVO orderVO = orderService.createOrder(shopCartList, submitOrderBO);
        String orderId = orderVO.getOrderId();
        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品

         /*
         1001
         2002 -> 用户购买
         3003 -> 用户购买
         4004
         */
        // 清理覆盖现有的redis汇总的购物数据
        if (!CollectionUtils.isEmpty(shopCartList)) {
            shopCartList.removeAll(orderVO.getToBeRemovedShopCartList());
            redisOperator.set(shopCartKey, JsonUtils.objectToJson(shopCartList));
        }
        // 整合redis之后，完善购物车中已结算商品清除，并且同步到前端的cookie
        CookieUtils.setCookie(request, response, EnumCookie.SHOP_CART.cookieName, JsonUtils.objectToJson(shopCartList), true);
        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAY_RETURN_URL);
        // 为了方便测试购买，所以所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(EnumPayCenter.USERNAME.headerName, EnumPayCenter.USERNAME.headerValue);
        headers.add(EnumPayCenter.PASSWORD.headerName, EnumPayCenter.PASSWORD.headerName);
        // 发送POST请求
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(PAYMENT_URL, entity, IMOOCJSONResult.class);
        IMOOCJSONResult paymentResult = responseEntity.getBody();
        if (Objects.nonNull(paymentResult) && HttpStatus.OK.value() != paymentResult.getStatus()) {
            return IMOOCJSONResult.errorMsg("支付中心订单创建失败，请联系管理员！");
        }
        return IMOOCJSONResult.ok(orderId);
    }

    @ApiOperation(value = "用户支付通知", notes = "通知商户已支付订单")
    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(
            @ApiParam(value = "商户订单ID")
            @RequestBody String merchantOrderId) {
        if (StringUtils.isBlank(merchantOrderId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        orderService.updateOrderStatus(merchantOrderId, EnumOrderStatus.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @ApiOperation(value = "查询生产系统支付中心的订单信息", notes = "提供给大家查询的方法，查询生产系统支付中心的订单信息")
    @GetMapping("getPaymentCenterOrderInfo")
    public IMOOCJSONResult getPaymentCenterOrderInfo(
            @ApiParam(value = "商户订单ID", required = true)
            @RequestParam String merchantOrderId,
            @ApiParam(value = "商户ID", required = true)
            @RequestParam String merchantUserId) {
        if (StringUtils.isBlank(merchantOrderId) || StringUtils.isBlank(merchantUserId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(EnumPayCenter.USERNAME.headerName, EnumPayCenter.USERNAME.headerValue);
        headers.add(EnumPayCenter.PASSWORD.headerName, EnumPayCenter.PASSWORD.headerName);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("merchantOrderId", merchantOrderId);
        multiValueMap.add("merchantUserId", merchantUserId);
        // 发送POST请求
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(multiValueMap, headers);
        ResponseEntity<IMOOCJSONResult> responseEntity = restTemplate.postForEntity(PAYMENT_CENTER_ORDER_INFO_URL, entity, IMOOCJSONResult.class);
        return IMOOCJSONResult.ok(responseEntity.getBody());
    }

    @ApiOperation(value = "查询用户支付订单信息", notes = "查询用户支付订单信息")
    @GetMapping("getPaidOrderInfo")
    public IMOOCJSONResult getPaidOrderInfo(
            @ApiParam(value = "订单ID")
            @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return IMOOCJSONResult.ok(orderStatus);
    }

}
