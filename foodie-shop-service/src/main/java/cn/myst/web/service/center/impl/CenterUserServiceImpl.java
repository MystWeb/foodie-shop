package cn.myst.web.service.center.impl;

import cn.myst.web.mapper.UsersMapper;
import cn.myst.web.pojo.Users;
import cn.myst.web.service.center.CenterUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ziming.xing
 * Create Date：2021/7/15
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CenterUserServiceImpl implements CenterUserService {
    private final UsersMapper usersMapper;

    private final Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        Users users = usersMapper.selectById(userId);
        users.setPassword(null);
        return users;
    }
}
