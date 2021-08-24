package cn.myst.web.controller;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumCookie;
import cn.myst.web.enums.EnumRedisKeys;
import cn.myst.web.enums.EnumUserValidation;
import cn.myst.web.pojo.Users;
import cn.myst.web.pojo.vo.UsersVO;
import cn.myst.web.service.UserService;
import cn.myst.web.utils.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

/**
 * @author ziming.xing
 * Create Date：2021/8/24
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SSOController {
    private final UserService userService;

    private final RedisOperator redisOperator;

    // 登录页面
    public static final String LOGIN_PAGE = "login";

    @GetMapping("/login")
    public String login(String returnUrl,
                        Model model,
                        HttpServletRequest request) {
        model.addAttribute("returnUrl", returnUrl);
        // 1. 获取userTicket门票，如果cookie中能够获取到，证明用户登录过，此时签发一个一次性的临时票据并且回跳
        String userTicket = CookieUtils.getCookieValue(request, EnumCookie.USER_TICKET.cookieName);
        boolean isVerifyUserTicket = userService.verifyUserTicket(userTicket);
        if (isVerifyUserTicket) {
            String tmpTicket = userService.createTmpTicket();
            return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
        }

        // 2. 用户从未登录过，第一次进入则跳转到CAS的统一登录页面
        return LOGIN_PAGE;
    }

    /**
     * CAS的统一登录接口
     * 目的：
     * 1. 登录后创建用户的全局会话                 ->  uniqueToken
     * 2. 创建用户全局门票，用以表示在CAS端是否登录  ->  userTicket
     * 3. 创建用户的临时票据，用于回跳回传          ->  tmpTicket
     */
    @PostMapping("/doLogin")
    public String doLogin(String username,
                          String password,
                          String returnUrl,
                          Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        model.addAttribute("returnUrl", returnUrl);

        // 1、 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            model.addAttribute("errMsg", EnumUserValidation.USERNAME_PASSWORD_IS_EMPTY.zh);
            return LOGIN_PAGE;
        }

        // 2、 实现登录用户
        Users users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (Objects.isNull(users)) {
            model.addAttribute("errMsg", EnumUserValidation.INCORRECT_USERNAME_OR_PASSWORD.zh);
            return LOGIN_PAGE;
        }

        // 3. 实现用户redis会话
        UsersVO usersVO = userService.convertUsersVO(users);
        redisOperator.set(EnumRedisKeys.USER_TOKEN.key + ":" + users.getId(), JsonUtils.objectToJson(usersVO));

        // 4. 生成ticket门票，全局门票，代表用户在CAS端登录过
        String userTicket = UUID.randomUUID().toString().trim();
        // 4.1 用户全局门票需要放入CAS端的cookie中
        CookieUtils.setCookie(request, response, EnumCookie.USER_TICKET.cookieName, userTicket, true);
        // 5. userTicket关联用户id，并且放入到redis中，代表这个用户有门票了，可以在各个景区游玩
        redisOperator.set(EnumRedisKeys.USER_TICKET.key + ":" + userTicket, users.getId());

        // 6. 生成临时票据，回跳到调用端网站，是由CAS端所签发的一个一次性的临时ticket
        String tmpTicket = userService.createTmpTicket();

        /*
        1、userTicket：用于表示用户在CAS端的一个登录状态为已经登录
        2、tmpTicket：用于颁发给用户进行一次性验证的票据，有时效性
        举例：
        我们去动物园玩耍，大门口买了一张统一的门票，这个就是CAS系统的全局门票和用户全局会话。
        动物园里有一些小的景点，需要凭你的门票去领取一次性的票据，有了这张票据以后就能去一些小的景点游玩了。
        这样的一个个的小景点其实就是我们这里所对应的一个个的站点。
        当我们使用完毕这张临时票据以后，就需要销毁。
         */
        return "redirect:" + returnUrl + "?tmpTicket=" + tmpTicket;
    }

    @PostMapping("/verifyTmpTicket")
    @ResponseBody
    public IMOOCJSONResult verifyTmpTicket(String tmpTicket,
                                           HttpServletRequest request) {
        // 使用一次性临时票据来验证用户是否登录，如果登录过，把用户会话信息返回给站点
        // 使用完毕后，需要销毁临时票据
        String tmpTicketKey = EnumRedisKeys.TMP_TICKET.key + ":" + tmpTicket;
        String tmpTicketValue = redisOperator.get(tmpTicketKey);
        if (StringUtils.isBlank(tmpTicketValue)) {
            return IMOOCJSONResult.errorUserTicket(EnumBaseException.USER_TICKET_EXCEPTION.zh);
        }

        // 1. 如果临时票据OK，则需要销毁，并且拿到CAS端cookie中的全局userTicket，以此再获取用户会话
        if (!tmpTicketValue.equals(MD5Utils.getMD5Str(tmpTicket))) {
            return IMOOCJSONResult.errorUserTicket(EnumBaseException.USER_TICKET_EXCEPTION.zh);
        } else {
            // 销毁临时票据
            redisOperator.del(tmpTicketKey);
        }

        // 2. 验证并且获取用户的userTicket
        String userTicket = CookieUtils.getCookieValue(request, EnumCookie.USER_TICKET.cookieName);
        String userId = redisOperator.get(EnumRedisKeys.USER_TICKET.key + ":" + userTicket);
        if (StringUtils.isEmpty(userId)) {
            return IMOOCJSONResult.errorUserTicket(EnumBaseException.USER_TICKET_EXCEPTION.zh);
        }

        // 3. 验证门票对应的user会话是否存在
        String userToken = redisOperator.get(EnumRedisKeys.USER_TOKEN.key + ":" + userId);
        if (StringUtils.isBlank(userToken)) {
            return IMOOCJSONResult.errorUserTicket(EnumBaseException.USER_TICKET_EXCEPTION.zh);
        }

        // 验证成功，返回OK，携带用户会话
        return IMOOCJSONResult.ok(JsonUtils.jsonToPojo(userToken, UsersVO.class));
    }

    @PostMapping("/logout")
    @ResponseBody
    public IMOOCJSONResult logout(String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        // 0. 获取CAS中的用户门票
        String userTicket = CookieUtils.getCookieValue(request, EnumCookie.USER_TICKET.cookieName);

        // 1. 清除userTicket票据，redis/cookie
        CookieUtils.deleteCookie(request, response, EnumCookie.USER_TICKET.cookieName);
        redisOperator.del(EnumRedisKeys.USER_TICKET.key + ":" + userTicket);

        // 2. 清除用户全局会话（分布式会话）
        redisOperator.del(EnumRedisKeys.USER_TOKEN.key + ":" + userId);

        return IMOOCJSONResult.ok();
    }
}
