package cn.myst.web.xa.demo.mapper.db132;

import cn.myst.web.xa.demo.domain.db132.XA132;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface XA132Mapper extends BaseMapper<XA132> {
    int updateBatchSelective(List<XA132> list);

    int batchInsert(@Param("list") List<XA132> list);
}