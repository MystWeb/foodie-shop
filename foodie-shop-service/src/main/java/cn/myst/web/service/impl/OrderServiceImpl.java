package cn.myst.web.service.impl;

import cn.myst.web.enums.EnumException;
import cn.myst.web.enums.EnumOrderStatus;
import cn.myst.web.enums.EnumYesOrNo;
import cn.myst.web.exception.BusinessException;
import cn.myst.web.mapper.OrderItemsMapper;
import cn.myst.web.mapper.OrderStatusMapper;
import cn.myst.web.mapper.OrdersMapper;
import cn.myst.web.pojo.*;
import cn.myst.web.pojo.bo.SubmitOrderBO;
import cn.myst.web.pojo.vo.MerchantOrdersVO;
import cn.myst.web.pojo.vo.OrderVO;
import cn.myst.web.service.AddressService;
import cn.myst.web.service.ItemService;
import cn.myst.web.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderServiceImpl implements OrderService {
    private final OrdersMapper ordersMapper;
    private final OrderItemsMapper orderItemsMapper;
    private final OrderStatusMapper orderStatusMapper;
    private final AddressService addressService;
    private final ItemService itemService;
    private final Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        // 包邮费用设置为0
        int postAmount = 0;
        // 生成订单id
        String orderId = this.sid.nextShort();
        // 1. 新订单数据保存
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        // 查询具体的用户收货地址，生成收货人信息快照
        UserAddress userAddress = addressService.queryUserAddress(userId, addressId);
        newOrder.setReceiverName(userAddress.getReceiver());
        newOrder.setReceiverMobile(userAddress.getMobile());
        newOrder.setReceiverAddress(userAddress.getProvince() + " "
                + userAddress.getCity() + " "
                + userAddress.getDistrict() + " "
                + userAddress.getDetail());
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsComment(EnumYesOrNo.NO.type);
        newOrder.setIsDelete(EnumYesOrNo.NO.type);
        // 获取当前时间
        Date nowDate = Date.from(Instant.now());
        newOrder.setCreatedTime(nowDate);
        newOrder.setUpdatedTime(nowDate);

        // 2. 循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        // 商品原价累计
        int totalAmount = 0;
        // 优惠后的实际支付价格累计
        int realPayAmount = 0;
        List<OrderItems> orderItemList = new ArrayList<>();
        for (String itemSpecId : itemSpecIdArr) {
            int buyCounts = 1;
            // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemsSpec = itemService.queryItemSpecBySpecId(itemSpecId);
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
            // 2.2 根据规格id，查询商品信息及商品图片
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemByItemId(itemId);
            String imgUrl = itemService.queryItemMainImgByItemId(itemId);
            // 2.3 循环生成子订单数据对象，并添加到List
            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
//            orderItemsMapper.insert(subOrderItem);
            orderItemList.add(subOrderItem);
            // 2.4 用户提交订单以后，规格表中需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }
        // 2.5 批量保存子订单数据
        orderItemsMapper.batchInsert(orderItemList);
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        // 2.6 保存订单
        ordersMapper.insert(newOrder);
        // 3. 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(EnumOrderStatus.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(nowDate);
        orderStatusMapper.insert(waitPayOrderStatus);

        // 4. 构建商户订单，用户传给用户中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        // 5. 构建自定义订单VO
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);

        return orderVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        if (StringUtils.isBlank(orderId) || Objects.isNull(orderStatus)) {
            throw new BusinessException(EnumException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        Date date = Date.from(Instant.now());
        paidStatus.setPayTime(date);
        orderStatusMapper.updateById(paidStatus);
    }
}
