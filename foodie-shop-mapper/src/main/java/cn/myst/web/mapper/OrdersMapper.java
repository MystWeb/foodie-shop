package cn.myst.web.mapper;

import cn.myst.web.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    int updateBatchSelective(List<Orders> list);

    int batchInsert(@Param("list") List<Orders> list);
}