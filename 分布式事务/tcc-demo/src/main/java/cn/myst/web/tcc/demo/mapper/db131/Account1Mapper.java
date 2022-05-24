package cn.myst.web.tcc.demo.mapper.db131;

import cn.myst.web.tcc.demo.domain.db131.Account1;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface Account1Mapper extends BaseMapper<Account1> {
    int updateBatchSelective(List<Account1> list);

    int batchInsert(@Param("list") List<Account1> list);
}