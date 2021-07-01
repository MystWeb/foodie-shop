package cn.myst.web.service;

import cn.myst.web.pojo.bo.SubmitOrderBO;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 */
public interface OrderService {
    /**
     * 创建订单相关信息
     */
    String createOrder(SubmitOrderBO submitOrderBO);
}
