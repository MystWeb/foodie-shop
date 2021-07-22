package cn.myst.web.service.center.impl;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumOrder;
import cn.myst.web.enums.EnumOrderStatus;
import cn.myst.web.enums.EnumYesOrNo;
import cn.myst.web.exception.BusinessException;
import cn.myst.web.mapper.OrderStatusMapper;
import cn.myst.web.mapper.OrdersCustomMapper;
import cn.myst.web.mapper.OrdersMapper;
import cn.myst.web.pojo.OrderStatus;
import cn.myst.web.pojo.Orders;
import cn.myst.web.pojo.vo.MyOrdersVO;
import cn.myst.web.pojo.vo.OrderStatusCountsVO;
import cn.myst.web.service.BaseService;
import cn.myst.web.service.center.MyOrdersService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.PagedGridResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyOrdersServiceImpl implements MyOrdersService {
    private final OrdersCustomMapper ordersCustomMapper;
    private final OrderStatusMapper orderStatusMapper;
    private final OrdersMapper ordersMapper;

    private final BaseService baseService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (Objects.nonNull(orderStatus)) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> list = ordersCustomMapper.queryMyOrders(map);
        return baseService.setterPagedGrid(page, list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        OrderStatus updateOrderStatus = new OrderStatus();
        // 更新订单状态为：已发货，待收货
        updateOrderStatus.setOrderStatus(EnumOrderStatus.WAIT_RECEIVE.type);
        updateOrderStatus.setDeliverTime(Date.from(Instant.now()));

        LambdaUpdateWrapper<OrderStatus> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OrderStatus::getOrderId, orderId);
        // 更新订单状态条件为：已付款，待发货
        updateWrapper.eq(OrderStatus::getOrderStatus, EnumOrderStatus.WAIT_DELIVER.type);
        orderStatusMapper.update(updateOrderStatus, updateWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId, userId);
        queryWrapper.eq(Orders::getId, orderId);
        queryWrapper.eq(Orders::getIsDelete, EnumYesOrNo.NO.type);
        return ordersMapper.selectOne(queryWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public IMOOCJSONResult checkUserOrder(String userId, String orderId) {
        Orders order = this.queryMyOrder(userId, orderId);
        if (Objects.isNull(order)) {
            return IMOOCJSONResult.errorMsg(EnumOrder.ORDER_DOES_NOT_EXIST.zh);
        }
        return IMOOCJSONResult.ok(order);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        if (StringUtils.isBlank(orderId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        OrderStatus updateOrderStatus = new OrderStatus();
        // 更新订单状态为：交易成功
        updateOrderStatus.setOrderStatus(EnumOrderStatus.SUCCESS.type);
        updateOrderStatus.setSuccessTime(Date.from(Instant.now()));

        LambdaUpdateWrapper<OrderStatus> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OrderStatus::getOrderId, orderId);
        // 更新订单状态条件为：已发货，待收货
        updateWrapper.eq(OrderStatus::getOrderStatus, EnumOrderStatus.WAIT_RECEIVE.type);
        int result = orderStatusMapper.update(updateOrderStatus, updateWrapper);
        return result == 1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        Orders updateOrders = new Orders();
        updateOrders.setIsDelete(EnumYesOrNo.YES.type);
        updateOrders.setUpdatedTime(Date.from(Instant.now()));

        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Orders::getId, orderId);
        updateWrapper.eq(Orders::getUserId, userId);
        int result = ordersMapper.update(updateOrders, updateWrapper);
        return result == 1;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 待付款订单数量
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderStatus", EnumOrderStatus.WAIT_PAY.type);
        int waitPayCounts = ordersCustomMapper.getMyOrderStatusCounts(map);

        // 待发货订单数量
        map.put("orderStatus", EnumOrderStatus.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersCustomMapper.getMyOrderStatusCounts(map);

        // 待收货订单数量
        map.put("orderStatus", EnumOrderStatus.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersCustomMapper.getMyOrderStatusCounts(map);

        // 待评价订单数量
        map.put("orderStatus", EnumOrderStatus.SUCCESS.type);
        map.put("isComment", EnumYesOrNo.NO.type);
        int waitCommentCounts = ordersCustomMapper.getMyOrderStatusCounts(map);

        return OrderStatusCountsVO.builder()
                .waitPayCounts(waitPayCounts)
                .waitDeliverCounts(waitDeliverCounts)
                .waitReceiveCounts(waitReceiveCounts)
                .waitCommentCounts(waitCommentCounts)
                .build();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getMyOrderTrend(String userId, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<OrderStatus> list = ordersCustomMapper.getMyOrderTrend(map);
        return baseService.setterPagedGrid(page, list);
    }
}
