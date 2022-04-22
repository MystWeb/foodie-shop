package cn.myst.web.distribute.demo.mapper;

import cn.myst.web.distribute.demo.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/4/20
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    int updateBatchSelective(List<Order> list);

    int batchInsert(@Param("list") List<Order> list);
}