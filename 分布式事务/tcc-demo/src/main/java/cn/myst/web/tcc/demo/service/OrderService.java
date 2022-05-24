package cn.myst.web.tcc.demo.service;

import cn.myst.web.tcc.demo.domain.db132.Order;
import cn.myst.web.tcc.demo.mapper.db132.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    /**
     * 订单回调接口
     *
     * @param orderId 订单id
     * @return 0:成功 1:订单不存在
     */
    public int handleOrder(int orderId) {
        Order order = orderMapper.selectById(orderId);

        throw new RuntimeException("系统异常");

//        if (order==null) return 1;
//        order.setOrderStatus(1);//已支付
//        order.setUpdateTime(new Date());
//        order.setUpdateUser(0);//系统更新
//        orderMapper.updateByPrimaryKey(order);
//
//        return 0;
    }

}