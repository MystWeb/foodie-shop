package cn.myst.web.service.center.impl;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumYesOrNo;
import cn.myst.web.exception.BusinessException;
import cn.myst.web.mapper.*;
import cn.myst.web.pojo.OrderItems;
import cn.myst.web.pojo.OrderStatus;
import cn.myst.web.pojo.Orders;
import cn.myst.web.pojo.bo.center.OrderItemsCommentBO;
import cn.myst.web.pojo.vo.MyCommentVO;
import cn.myst.web.service.BaseService;
import cn.myst.web.service.center.MyCommentsService;
import cn.myst.web.utils.PagedGridResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ziming.xing
 * Create Date：2021/7/21
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyCommentsServiceImpl implements MyCommentsService {
    private final OrderItemsMapper orderItemsMapper;
    private final ItemsCommentsMapper itemsCommentsMapper;
    private final ItemsCommentsCustomMapper itemsCommentsCustomMapper;
    private final OrdersMapper ordersMapper;
    private final OrderStatusMapper orderStatusMapper;
    private final BaseService baseService;

    private final Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        LambdaQueryWrapper<OrderItems> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderItems::getOrderId, orderId);
        return orderItemsMapper.selectList(queryWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String userId, String orderId, List<OrderItemsCommentBO> commentList) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId) || CollectionUtils.isEmpty(commentList)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 获取当前时间
        Date date = Date.from(Instant.now());
        // 1. 保存评价 items_comments
        commentList.forEach(orderItemsCommentBO -> orderItemsCommentBO.setCommentId(sid.nextShort()));
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsCustomMapper.saveComments(map);
        /*List<ItemsComments> insertList = new ArrayList<>();
        commentList.forEach(orderItemsCommentBO -> {
            ItemsComments itemsComments = new ItemsComments();
            BeanUtils.copyProperties(orderItemsCommentBO, itemsComments);
            String commentId = sid.nextShort();
            itemsComments.setId(commentId);
            itemsComments.setSepcName(orderItemsCommentBO.getItemSpecName());
            itemsComments.setCreatedTime(date);
            itemsComments.setUpdatedTime(date);
            insertList.add(itemsComments);
        });
        itemsCommentsMapper.batchInsert(insertList);*/
        // 2. 修改订单表改已评价 orders
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(EnumYesOrNo.YES.type);
        ordersMapper.updateById(orders);
        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(date);
        orderStatusMapper.updateById(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsCustomMapper.queryMyComments(map);
        return baseService.setterPagedGrid(page, list);
    }
}
