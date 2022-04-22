package cn.myst.web.distribute.demo.mapper;

import cn.myst.web.distribute.demo.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/4/20
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    int updateBatchSelective(List<Product> list);

    int batchInsert(@Param("list") List<Product> list);
}