package cn.myst.web.service.center;

import cn.myst.web.pojo.OrderItems;
import cn.myst.web.pojo.bo.center.OrderItemsCommentBO;
import cn.myst.web.utils.PagedGridResult;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/7/21
 */
public interface MyCommentsService {
    /**
     * 根据订单id，查询关联的商品
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     */
    void saveComments(String userId, String orderId, List<OrderItemsCommentBO> commentList);

    /**
     * 查询我的评价列表-分页
     */
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
