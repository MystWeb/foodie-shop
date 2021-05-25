package cn.myst.web.mapper;
import cn.myst.web.pojo.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {
    int updateBatchSelective(List<Users> list);

    int batchInsert(@Param("list") List<Users> list);
}