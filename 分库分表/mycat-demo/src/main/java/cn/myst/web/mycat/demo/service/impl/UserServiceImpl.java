package cn.myst.web.mycat.demo.service.impl;

import cn.myst.web.mycat.demo.domain.User;
import cn.myst.web.mycat.demo.mapper.UserMapper;
import cn.myst.web.mycat.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Override
    public int updateBatchSelective(List<User> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<User> list) {
        return baseMapper.batchInsert(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public void testUser() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("奇数");
        userMapper.insert(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("偶数111");
        userMapper.insert(user2);
    }
}
