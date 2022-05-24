package cn.myst.web.tcc.demo.mapper.db131;

import cn.myst.web.tcc.demo.domain.db131.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int updateBatchSelective(List<User> list);

    int batchInsert(@Param("list") List<User> list);
}