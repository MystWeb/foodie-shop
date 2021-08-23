package cn.myst.web.service.impl;

import cn.myst.web.enums.EnumCookie;
import cn.myst.web.enums.EnumRedisKeys;
import cn.myst.web.enums.EnumSex;
import cn.myst.web.mapper.UsersMapper;
import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.bo.ShopcartBO;
import cn.myst.web.pojo.bo.UserBO;
import cn.myst.web.pojo.vo.UsersVO;
import cn.myst.web.service.UserService;
import cn.myst.web.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.*;

/**
 * @author ziming.xing
 * Create Date：2021/5/19
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {
    private final UsersMapper usersMapper;
    private final RedisOperator redisOperator;

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
        // 获取当前时间
        Date nowDate = Date.from(Instant.now());
        users.setCreatedTime(nowDate);
        users.setUpdatedTime(nowDate);
        usersMapper.insert(users);
        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return null;
        }
        LambdaQueryWrapper<Users> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Users::getUsername, username);
        queryWrapper.eq(Users::getPassword, password);
        return usersMapper.selectOne(queryWrapper);
    }

    @Override
    public void syncShopCartData(String userId, HttpServletRequest request, HttpServletResponse response) {
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

    @Override
    public void setNullProperty(Users users) {
        users.setPassword(null);
        users.setMobile(null);
        users.setEmail(null);
        users.setCreatedTime(null);
        users.setUpdatedTime(null);
        users.setBirthday(null);
    }

    @Override
    public UsersVO convertUsersVO(Users users) {
        String userUniqueToken = UUID.randomUUID().toString().trim();
        String md5UserUniqueToken = MD5Utils.getMD5Str(userUniqueToken);
        redisOperator.set(EnumRedisKeys.USER_TOKEN.key + ":" + users.getId(), md5UserUniqueToken);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setUserUniqueToken(userUniqueToken);
        return usersVO;
    }
}
