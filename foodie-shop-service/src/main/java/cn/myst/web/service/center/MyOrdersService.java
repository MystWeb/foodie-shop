package cn.myst.web.service.center;

import cn.myst.web.pojo.Orders;
import cn.myst.web.pojo.vo.OrderStatusCountsVO;
import cn.myst.web.utils.IMOOCJSONResult;
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

    /**
     * 更新订单状态 -> 商家发货
     */
    void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 用于验证用户和订单是否有关联关系，避免非法调用
     */
    IMOOCJSONResult checkUserOrder(String userId, String orderId);

    /**
     * 更新订单状态 -> 确认收货
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单（逻辑删除）
     */
    boolean deleteOrder(String userId, String orderId);

    /**
     * 查询用户订单数
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 获得订单动向-分页
     */
    PagedGridResult getMyOrderTrend(String userId, Integer page, Integer pageSize);
}
