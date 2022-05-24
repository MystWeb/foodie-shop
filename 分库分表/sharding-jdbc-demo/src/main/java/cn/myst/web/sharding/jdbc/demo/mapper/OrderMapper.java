package cn.myst.web.sharding.jdbc.demo.mapper;

import cn.myst.web.sharding.jdbc.demo.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    int updateBatchSelective(List<Order> list);

    int batchInsert(@Param("list") List<Order> list);
}