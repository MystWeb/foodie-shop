package cn.myst.web.sharding.jdbc.demo.mapper;

import cn.myst.web.sharding.jdbc.demo.domain.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface AreaMapper extends BaseMapper<Area> {
    int updateBatchSelective(List<Area> list);

    int batchInsert(@Param("list") List<Area> list);
}