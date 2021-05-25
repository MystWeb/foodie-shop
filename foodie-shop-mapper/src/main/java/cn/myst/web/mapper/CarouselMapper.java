package cn.myst.web.mapper;

import cn.myst.web.pojo.Carousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface CarouselMapper extends BaseMapper<Carousel> {
    int updateBatchSelective(List<Carousel> list);

    int batchInsert(@Param("list") List<Carousel> list);
}