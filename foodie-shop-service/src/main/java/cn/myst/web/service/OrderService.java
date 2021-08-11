package cn.myst.web.service;

import cn.myst.web.pojo.OrderStatus;
import cn.myst.web.pojo.bo.ShopcartBO;
import cn.myst.web.pojo.bo.SubmitOrderBO;
import cn.myst.web.pojo.vo.OrderVO;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 */
public interface OrderService {
    /**
     * 创建订单相关信息
     */
    OrderVO createOrder(List<ShopcartBO> shopCartList, SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
