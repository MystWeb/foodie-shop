package cn.myst.web.tcc.demo.service;

import cn.myst.web.tcc.demo.domain.db131.User;
import cn.myst.web.tcc.demo.mapper.db131.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {
    private final UserMapper userMapper;
    private final CuratorFramework zkClient;

    public List<User> getAllUsers() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        return userMapper.selectList(queryWrapper);
    }

    public int delUser(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            log.info("用户存在，用户为：" + userId);
            return userMapper.deleteById(userId);
        }
        log.info("用户不存在存在，用户为：" + userId);
        return 0;
    }

    public User selectById(Integer userId) {
        return userMapper.selectById(userId);
    }

    public int updateUser(User user) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, user.getId())
                .eq(User::getVersion, user.getVersion());
        return userMapper.update(user, updateWrapper);
    }

    public int insertUser(User user, String token) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(zkClient, "/" + token);
        boolean isLock = lock.acquire(30, TimeUnit.SECONDS);
        if (isLock) {
            return userMapper.insert(user);
        }
        return 0;
    }
}