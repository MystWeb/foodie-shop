package cn.myst.web.tcc.demo.mapper.db132;

import cn.myst.web.tcc.demo.domain.db132.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    int updateBatchSelective(List<Order> list);

    int batchInsert(@Param("list") List<Order> list);
}