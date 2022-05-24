package cn.myst.web.xa.demo.mapper.db131;

import cn.myst.web.xa.demo.domain.db131.XA131;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface XA131Mapper extends BaseMapper<XA131> {
    int updateBatchSelective(List<XA131> list);

    int batchInsert(@Param("list") List<XA131> list);
}