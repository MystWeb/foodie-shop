package cn.myst.web.mapper;

import cn.myst.web.pojo.ItemsImg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface ItemsImgMapper extends BaseMapper<ItemsImg> {
    int updateBatchSelective(List<ItemsImg> list);

    int batchInsert(@Param("list") List<ItemsImg> list);
}