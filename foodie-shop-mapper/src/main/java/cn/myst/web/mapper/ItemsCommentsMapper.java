package cn.myst.web.mapper;

import cn.myst.web.pojo.ItemsComments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface ItemsCommentsMapper extends BaseMapper<ItemsComments> {
    int updateBatchSelective(List<ItemsComments> list);

    int batchInsert(@Param("list") List<ItemsComments> list);
}