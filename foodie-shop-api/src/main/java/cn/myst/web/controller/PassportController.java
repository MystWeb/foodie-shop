package cn.myst.web.controller;

import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.UserBO;
import cn.myst.web.service.UserService;
import cn.myst.web.utils.CookieUtils;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.JsonUtils;
import cn.myst.web.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/passport")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PassportController {
    private final UserService userService;

    public static final String USER_REGISTER_INFO_EMPTY = "用户注册信息不能为空";
    public static final String USERNAME_IS_EMPTY = "用户名不能为空";
    public static final String USERNAME_PASSWORD_IS_EMPTY = "用户名或密码不能为空";
    public static final String USERNAME_ALREADY_EXISTS = "用户名已经存在";
    public static final String PASSWORD_LENGTH_ERROR = "密码长度不能少于6位数或大于20位数";
    public static final String INCONSISTENT_PASSWORDS = "两次密码输入不一致";
    public static final String INCORRECT_USERNAME_OR_PASSWORD = "用户名或密码错误";
    public static final String USER_COOKIE_NAME = "user";

    @ApiOperation(value = "用户名是否存在")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {
        // 1、 判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg(USERNAME_IS_EMPTY);
        }
        // 2、 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg(USERNAME_ALREADY_EXISTS);
        }
        // 3、 请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @ApiOperation("用户注册")
    @PostMapping("/regist")
    public IMOOCJSONResult register(@RequestBody UserBO userBO,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        // 0、判断用户注册信息必须不为空
        if (Objects.isNull(userBO)) {
            return IMOOCJSONResult.errorMsg(USER_REGISTER_INFO_EMPTY);
        }

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        // 1、 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(confirmPassword)) {
            return IMOOCJSONResult.errorMsg(USERNAME_PASSWORD_IS_EMPTY);
        }

        // 2、 查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg(USERNAME_ALREADY_EXISTS);
        }

        // 3、 密码长度不能少于6位数或大于20位数
        if (password.length() < 6 || password.length() > 20) {
            return IMOOCJSONResult.errorMsg(PASSWORD_LENGTH_ERROR);
        }

        // 4、 判断两次密码是否一致
        if (!Objects.equals(password, confirmPassword)) {
            return IMOOCJSONResult.errorMsg(INCONSISTENT_PASSWORDS);
        }
        // 5、 实现注册用户
        Users user = userService.createUser(userBO);

        // 设置用户隐私信息为空
        setNullProperty(user);

        CookieUtils.setCookie(request, response, USER_COOKIE_NAME, JsonUtils.objectToJson(user));

        return IMOOCJSONResult.ok(user);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // 0、判断用户注册信息必须不为空
        if (Objects.isNull(userBO)) {
            return IMOOCJSONResult.errorMsg(USER_REGISTER_INFO_EMPTY);
        }

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 1、 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg(USERNAME_PASSWORD_IS_EMPTY);
        }

        // 2、 实现登录用户
        Users user = userService.querUserForLogin(username, MD5Utils.getMD5Str(password));

        if (Objects.isNull(user)) {
            return IMOOCJSONResult.errorMsg(INCORRECT_USERNAME_OR_PASSWORD);
        }

        // 设置用户隐私信息为空
        setNullProperty(user);

        CookieUtils.setCookie(request, response, USER_COOKIE_NAME, JsonUtils.objectToJson(user));

        return IMOOCJSONResult.ok(user);
    }

    /**
     * 设置用户隐私信息为空
     */
    private void setNullProperty(Users user) {
        user.setPassword(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);
        user.setBirthday(null);
    }

    @ApiOperation("用户退出登录")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, USER_COOKIE_NAME);

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();
    }
}
