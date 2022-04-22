package cn.myst.web.distribute.mapper;

import cn.myst.web.distribute.domain.DistributeLock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/4/21
 */
@Mapper
public interface DistributeLockMapper extends BaseMapper<DistributeLock> {
    int updateBatchSelective(List<DistributeLock> list);

    int batchInsert(@Param("list") List<DistributeLock> list);
}