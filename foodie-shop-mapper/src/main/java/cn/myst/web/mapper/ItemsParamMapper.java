package cn.myst.web.mapper;

import cn.myst.web.pojo.ItemsParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface ItemsParamMapper extends BaseMapper<ItemsParam> {
    int updateBatchSelective(List<ItemsParam> list);

    int batchInsert(@Param("list") List<ItemsParam> list);
}