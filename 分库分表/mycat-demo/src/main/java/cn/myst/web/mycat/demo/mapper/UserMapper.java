package cn.myst.web.mycat.demo.mapper;

import cn.myst.web.mycat.demo.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int updateBatchSelective(List<User> list);

    int batchInsert(@Param("list") List<User> list);
}