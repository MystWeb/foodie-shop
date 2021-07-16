package cn.myst.web.service.center;

import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.center.CenterUserBO;

/**
 * @author ziming.xing
 * Create Date：2021/7/15
 */
public interface CenterUserService {
    /**
     * 根据用户id查询用户信息
     */
    Users queryUserInfo(String userId);

    /**
     * 更新用户信息
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);


}
