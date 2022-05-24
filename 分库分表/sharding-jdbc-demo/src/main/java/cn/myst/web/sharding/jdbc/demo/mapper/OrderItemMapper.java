package cn.myst.web.sharding.jdbc.demo.mapper;

import cn.myst.web.sharding.jdbc.demo.domain.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    int updateBatchSelective(List<OrderItem> list);

    int batchInsert(@Param("list") List<OrderItem> list);

    /**
     * ShadingJdbc 绑定表
     *
     * @param orderId 订单id
     * @return List<OrderItem>
     */
    @Select("select * from order o " +
            "inner join order_item oi " +
            "on o.user_id = oi.user_id and o.order_id = oi.order_id where o.order_id = #{0}")
    List<OrderItem> selectByOrderIdAndUserId(int orderId);
}