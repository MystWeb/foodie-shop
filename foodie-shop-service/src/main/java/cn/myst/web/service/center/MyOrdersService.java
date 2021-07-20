package cn.myst.web.service.center;

import cn.myst.web.utils.PagedGridResult;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
public interface MyOrdersService {

    /**
     * 查询我的订单列表
     */
    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);
}
