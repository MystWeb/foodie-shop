package cn.myst.web.service;

import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.UserBO;

/**
 * @author ziming.xing
 * Create Date：2021/5/19
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     */
    Users createUser(UserBO userBO);

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    Users querUserForLogin(String username, String password);
}
