package cn.myst.web.service;

import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.UserBO;
import cn.myst.web.pojo.vo.UsersVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ziming.xing
 * Create Date：2021/5/19
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     * 优化方案：SQL不再使用count，而是改用LIMIT 1，让数据库查询时遇到一条就返回，不再继续查找还有多少条了
     * SQL写法：SELECT 0 FROM TableName WHERE a = 1 AND b = 2 LIMIT 1
     * JAVA写法：Integer exist = xxxMapper.existXxxByXxx(params);
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     */
    Users createUser(UserBO userBO);

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    Users queryUserForLogin(String username, String password);

    /**
     * 注册登录成功后，同步cookie和redis中的购物车数据
     * 一、redis中无购物车数据，
     * 1、如果cookie中的购物车为空，那么这个时候不做任何处理
     * 2、如果cookie中的购物车不为空，此时直接放入redis中
     * 二、redis中有购物车数据，
     * 1、如果cookie中的购物车为空，那么直接把redis的购物车数据覆盖本地cookie
     * 2、如果cookie中的购物车不为空，cookie中的某个商品在redis中存在，
     * 则以cookie为主，删除redis中的数据，把cookie中的商品直接覆盖redis中（参考京东）
     * 三、同步到redis中以后，覆盖本地cookie购物车的数据，保证本地购物车的数据是同步最新的
     */
    void syncShopCartData(String userId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 设置用户隐私信息为空
     */
    void setNullProperty(Users user);

    /**
     * Users转换UsersVO
     * 1、生成md5加密token
     * 2、保存token到redis
     * 3、Users转换UsersVO
     */
    UsersVO convertUsersVO(Users users);

    /**
     * 创建临时票据
     */
    String createTmpTicket();

    /**
     * 验证用户门票
     */
    boolean verifyUserTicket(String userTicket);
}
