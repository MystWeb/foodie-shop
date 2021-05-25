package cn.myst.web.mapper;

import cn.myst.web.pojo.ItemsSpec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface ItemsSpecMapper extends BaseMapper<ItemsSpec> {
    int updateBatchSelective(List<ItemsSpec> list);

    int batchInsert(@Param("list") List<ItemsSpec> list);
}