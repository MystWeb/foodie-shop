package cn.myst.web.service.impl;

import cn.myst.web.enums.EnumSex;
import cn.myst.web.mapper.UsersMapper;
import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.UserBO;
import cn.myst.web.service.UserService;
import cn.myst.web.utils.DateUtil;
import cn.myst.web.utils.MD5Utils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/5/19
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {
    private final UsersMapper usersMapper;

    private final Sid sid;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
    private static final String USER_BIRTHDAY = "1900-01-01";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        if (StringUtils.isBlank(username)) {
            return false;
        }
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, username);
        return usersMapper.selectCount(queryWrapper) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {
        if (Objects.isNull(userBO)
                || StringUtils.isBlank(userBO.getUsername())
                || StringUtils.isBlank(userBO.getPassword())
                || userBO.getPassword().length() < 6
                || userBO.getPassword().length() > 20
                || StringUtils.isBlank(userBO.getConfirmPassword())
                || !Objects.equals(userBO.getPassword(), userBO.getConfirmPassword())) {
            return null;
        }

        String userId = sid.nextShort();

        Users users = new Users();
        // 分布式唯一ID
        users.setId(userId);
        // 用户名
        users.setUsername(userBO.getUsername());
        // 对密码进行 md5 加密
        users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        // 默认用户昵称同用户名
        users.setNickname(userBO.getUsername());
        // 默认头像
        users.setFace(USER_FACE);
        // 默认生日
        users.setBirthday(DateUtil.stringToDate(USER_BIRTHDAY));
        // 默认性别
        users.setSex(EnumSex.SECRET.type);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        usersMapper.insert(users);
        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users querUserForLogin(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return null;
        }
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, username);
        queryWrapper.eq(Users::getPassword, password);
        return usersMapper.selectOne(queryWrapper);
    }
}
