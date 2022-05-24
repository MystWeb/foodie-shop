package cn.myst.web.sharding.jdbc.demo;

import cn.myst.web.sharding.jdbc.demo.domain.Area;
import cn.myst.web.sharding.jdbc.demo.domain.Order;
import cn.myst.web.sharding.jdbc.demo.domain.OrderItem;
import cn.myst.web.sharding.jdbc.demo.mapper.AreaMapper;
import cn.myst.web.sharding.jdbc.demo.mapper.OrderItemMapper;
import cn.myst.web.sharding.jdbc.demo.mapper.OrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class ShardingJdbcDemoApplicationTests {

    private final OrderMapper orderMapper;
    private final AreaMapper areaMapper;
    private final OrderItemMapper orderItemMapper;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    public void testOrder() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setUserId(15);
        order.setOrderAmount(BigDecimal.TEN);
        order.setOrderStatus(1);
        orderMapper.insert(order);

        Order order2 = new Order();
        order2.setOrderId(2L);
        order2.setUserId(16);
        order2.setOrderAmount(BigDecimal.TEN);
        order2.setOrderStatus(1);
        orderMapper.insert(order2);

        throw new RuntimeException("test XA");
    }

    @Test
    public void testSelectOrder() {
        LambdaQueryWrapper<Order> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order::getOrderId, 4L)
                .eq(Order::getUserId, 20);
        List<Order> orders = orderMapper.selectList(lambdaQueryWrapper);
        orders.forEach(o -> System.out.println(o.getOrderId() + "----" + o.getUserId()));
    }

    @Test
    public void testGlobal() {
        Area area = new Area();
        area.setId(2);
        area.setName("上海");
        areaMapper.insert(area);
    }

    @Test
    public void testSelectGlobal() {
        LambdaQueryWrapper<Area> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Area::getId, 2);
        List<Area> areas = areaMapper.selectList(queryWrapper);
        System.out.println("area.size() = " + areas.size());

    }

    @Test
    public void testSelectBinding() {
        List<OrderItem> orderItems = orderItemMapper.selectByOrderIdAndUserId(1);
        System.out.println(orderItems.size());
    }

    @Test
    public void testOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(2);
        orderItem.setOrderId(1);
        orderItem.setProductName("测试商品");
        orderItem.setNum(1);
        orderItem.setUserId(19);
        orderItemMapper.insert(orderItem);
    }

    @Test
    public void testMsOrder() {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderId, 4L)
                .eq(Order::getUserId, 20);
        for (int i = 0; i < 10; i++) {
            List<Order> orders = orderMapper.selectList(queryWrapper);
            orders.forEach(o -> {
                System.out.println("orderId:" + o.getOrderId());
                System.out.println("userId:" + o.getUserId());
                System.out.println("orderAmount:" + o.getOrderAmount());
            });
        }
    }

}
