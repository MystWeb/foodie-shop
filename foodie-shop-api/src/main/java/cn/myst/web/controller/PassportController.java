package cn.myst.web.controller;

import cn.myst.web.enums.EnumCookie;
import cn.myst.web.enums.EnumRedisKeys;
import cn.myst.web.enums.EnumUserValidation;
import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.ShopcartBO;
import cn.myst.web.pojo.bo.UserBO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Users user = userService.createUser(userBO);

        // 设置用户隐私信息为空
        this.setNullProperty(user);

        CookieUtils.setCookie(request, response, EnumCookie.USER.cookieName, JsonUtils.objectToJson(user), true);

        // TODO 生成用户token，存入redis会话
        // 同步购物车数据
        this.syncShopCartData(user.getId(), request, response);

        return IMOOCJSONResult.ok(user);
    }

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
    private void syncShopCartData(String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        // 从redis中获取购物车
        String shopCartKey = EnumRedisKeys.SHOP_CART.key + ":" + userId;
        String shopCartCookieName = EnumCookie.SHOP_CART.cookieName;
        String shopCartJsonRedis = redisOperator.get(shopCartKey);
        // 从cookie中获取购物车
        String shopCartJsonCookie = CookieUtils.getCookieValue(request, EnumCookie.SHOP_CART.cookieName, true);
        // redis为空，cookie不为空，直接把cookie中的数据放入redis
        if (StringUtils.isBlank(shopCartJsonRedis)) {
            if (StringUtils.isNotBlank(shopCartJsonCookie)) {
                redisOperator.set(shopCartKey, shopCartJsonCookie);
            }
        } else {
            // redis不为空，cookie不为空，合并cookie和redis中购物车的商品数据（同一商品则覆盖redis）
            if (StringUtils.isNotBlank(shopCartJsonCookie)) {
                /*
                 * 1. 已经存在的，把cookie中对应的数量，覆盖redis（参考京东）
                 * 2. 该项商品标记为待删除，统一放入一个待删除的list
                 * 3. 从cookie中清理所有的待删除list
                 * 4. 合并redis和cookie中的数据
                 * 5. 更新到redis和cookie中
                 */
                List<ShopcartBO> shopCartListRedis = Optional.ofNullable(JsonUtils.jsonToList(shopCartJsonRedis, ShopcartBO.class))
                        .orElse(new ArrayList<>());
                List<ShopcartBO> shopCartListCookie = Optional.ofNullable(JsonUtils.jsonToList(shopCartJsonCookie, ShopcartBO.class))
                        .orElse(new ArrayList<>());
                // 定义一个待删除list
                List<ShopcartBO> pendingDeleteList = new ArrayList<>();

                shopCartListRedis.forEach(shopCartRedis -> {
                    String redisSpecId = shopCartRedis.getSpecId();

                    shopCartListCookie.forEach(shopCartCookie -> {
                        String cookieSpecId = shopCartCookie.getSpecId();

                        if (Objects.equals(redisSpecId, cookieSpecId)) {
                            // 覆盖购买数量，不累加购买数量，参考京东
                            shopCartRedis.setBuyCounts(shopCartCookie.getBuyCounts());
                            // 把shopCartCookie放入待删除列表，用于最后的删除与合并
                            pendingDeleteList.add(shopCartCookie);
                        }
                    });
                });
                // 从现有cookie中删除对应的覆盖过的商品数据
                shopCartListCookie.removeAll(pendingDeleteList);
                // 合并两个list
                shopCartListRedis.addAll(shopCartListCookie);
                // 更新到redis和cookie
                CookieUtils.setCookie(request, response, shopCartCookieName, JsonUtils.objectToJson(shopCartListRedis), true);
                redisOperator.set(shopCartKey, JsonUtils.objectToJson(shopCartListRedis));
            } else {
                // redis不为空，cookie为空，直接把redis覆盖cookie
                CookieUtils.setCookie(request, response, shopCartCookieName, shopCartJsonRedis, true);
            }
        }
    }

    @ApiOperation("用户登录")
    @PostMapping("login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // 0、判断用户注册信息必须不为空
        if (Objects.isNull(userBO)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.USER_REGISTER_INFO_EMPTY.zh);
        }

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 1、 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.USERNAME_PASSWORD_IS_EMPTY.zh);
        }

        // 2、 实现登录用户
        Users user = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (Objects.isNull(user)) {
            return IMOOCJSONResult.errorMsg(EnumUserValidation.INCORRECT_USERNAME_OR_PASSWORD.zh);
        }

        // 设置用户隐私信息为空
        this.setNullProperty(user);

        CookieUtils.setCookie(request, response, EnumCookie.USER.cookieName, JsonUtils.objectToJson(user), true);

        // TODO 生成用户token，存入redis会话
        // 同步购物车数据
        this.syncShopCartData(user.getId(), request, response);

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
    @PostMapping("logout")
    public IMOOCJSONResult logout(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            HttpServletRequest request,
            HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, EnumCookie.USER.cookieName);

        // 用户退出登录，需要清空购物车
        CookieUtils.deleteCookie(request, response, EnumCookie.SHOP_CART.cookieName);
        // TODO 分布式会话中需要清除用户数据

        return IMOOCJSONResult.ok();
    }
}
