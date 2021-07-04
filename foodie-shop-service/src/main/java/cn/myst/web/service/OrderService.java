package cn.myst.web.service;

import cn.myst.web.pojo.bo.SubmitOrderBO;
import cn.myst.web.pojo.vo.OrderVO;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 */
public interface OrderService {
    /**
     * 创建订单相关信息
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);
}
