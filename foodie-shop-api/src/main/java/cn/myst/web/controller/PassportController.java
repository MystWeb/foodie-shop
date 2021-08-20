package cn.myst.web.controller;

import cn.myst.web.enums.EnumCookie;
import cn.myst.web.enums.EnumRedisKeys;
import cn.myst.web.enums.EnumUserValidation;
import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.UserBO;
import cn.myst.web.pojo.vo.UsersVO;
import cn.myst.web.service.UserService;
import cn.myst.web.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Api(value = "注册登录", tags = "用户注册登录的相关接口")
@RestController
@RequestMapping("passport")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PassportController {
    private final UserService userService;
    private final RedisOperator redisOperator;

    @ApiOperation(value = "用户名是否存在")
    @GetMapping("usernameIsExist")
    public IMOOCJSONResult usernameIsExist(
            @ApiParam(value = "用户名", required = true)
            @RequestParam String username) {
        // 1、 判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.USERNAME_IS_EMPTY.zh);
        }
        // 2、 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.USERNAME_ALREADY_EXISTS.zh);
        }
        // 3、 请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @ApiOperation("用户注册")
    @PostMapping("register")
    public IMOOCJSONResult register(@RequestBody UserBO userBO,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        // 0、判断用户注册信息必须不为空
        if (Objects.isNull(userBO)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.USER_REGISTER_INFO_EMPTY.zh);
        }

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        // 1、 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(confirmPassword)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.USERNAME_PASSWORD_IS_EMPTY.zh);
        }

        // 2、 查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.USERNAME_ALREADY_EXISTS.zh);
        }

        // 3、 密码长度不能少于6位数或大于20位数
        if (password.length() < 6 || password.length() > 20) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.PASSWORD_LENGTH_ERROR.zh);
        }

        // 4、 判断两次密码是否一致
        if (!Objects.equals(password, confirmPassword)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.INCONSISTENT_PASSWORDS.zh);
        }
        // 5、 实现注册用户
        Users users = userService.createUser(userBO);

        // 设置用户隐私信息为空
        userService.setNullProperty(users);

        // 实现用户redis会话
        UsersVO usersVO = userService.convertUsersVO(users);

        CookieUtils.setCookie(request, response, EnumCookie.USER.cookieName, JsonUtils.objectToJson(usersVO), true);

        // 同步购物车数据
        userService.syncShopCartData(users.getId(), request, response);

        return IMOOCJSONResult.ok(users);
    }

    @ApiOperation("用户登录")
    @PostMapping("login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // 0、判断用户登录信息必须不为空
        if (Objects.isNull(userBO)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.USERNAME_PASSWORD_IS_EMPTY.zh);
        }

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 1、 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.USERNAME_PASSWORD_IS_EMPTY.zh);
        }

        // 2、 实现登录用户
        Users users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (Objects.isNull(users)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.INCORRECT_USERNAME_OR_PASSWORD.zh);
        }

        // 设置用户隐私信息为空
        userService.setNullProperty(users);

        // 实现用户redis会话
        UsersVO usersVO = userService.convertUsersVO(users);

        CookieUtils.setCookie(request, response, EnumCookie.USER.cookieName, JsonUtils.objectToJson(usersVO), true);

        // 同步购物车数据
        userService.syncShopCartData(users.getId(), request, response);

        return IMOOCJSONResult.ok(users);
    }

    @ApiOperation("用户退出登录")
    @PostMapping("logout")
    public IMOOCJSONResult logout(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, EnumCookie.USER.cookieName);

        // 分布式会话中需要清除用户数据—清除redis中user的会话信息
        redisOperator.del(EnumRedisKeys.USER_TOKEN.getKey() + ":" + userId);

        // 用户退出登录，需要清空购物车
        CookieUtils.deleteCookie(request, response, EnumCookie.SHOP_CART.cookieName);

        return IMOOCJSONResult.ok();
    }
}
