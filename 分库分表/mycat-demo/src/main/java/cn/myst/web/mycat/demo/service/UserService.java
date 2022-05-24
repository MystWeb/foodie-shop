package cn.myst.web.mycat.demo.service;

import cn.myst.web.mycat.demo.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
public interface UserService extends IService<User> {

    int updateBatchSelective(List<User> list);

    int batchInsert(List<User> list);

    void testUser();

}
