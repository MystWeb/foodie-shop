package cn.myst.web.tcc.demo.mapper.db132;

import cn.myst.web.tcc.demo.domain.db132.Account2;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface Account2Mapper extends BaseMapper<Account2> {
    int updateBatchSelective(List<Account2> list);

    int batchInsert(@Param("list") List<Account2> list);
}