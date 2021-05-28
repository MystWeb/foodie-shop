package cn.myst.web.mapper;

import cn.myst.web.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    int updateBatchSelective(List<Category> list);

    int batchInsert(@Param("list") List<Category> list);
}