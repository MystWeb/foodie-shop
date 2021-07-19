package cn.myst.web.service.center.impl;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.exception.BusinessException;
import cn.myst.web.mapper.UsersMapper;
import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.center.CenterUserBO;
import cn.myst.web.service.center.CenterUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/7/15
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CenterUserServiceImpl implements CenterUserService {
    private final UsersMapper usersMapper;

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        if (StringUtils.isBlank(userId) || Objects.isNull(centerUserBO)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 更新用户信息
        Users updateUser = new Users();
        BeanUtils.copyProperties(centerUserBO, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(Date.from(Instant.now()));
        usersMapper.updateById(updateUser);
        // 查询更新后的用户信息
        return this.queryUserInfo(userId);
    }

    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(faceUrl)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 更新用户信息
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setFace(faceUrl);
        updateUser.setUpdatedTime(Date.from(Instant.now()));
        usersMapper.updateById(updateUser);
        // 查询更新后的用户信息
        return this.queryUserInfo(userId);
    }
}
