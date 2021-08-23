package cn.myst.web.controller.interceptor;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumRedisKeys;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.JsonUtils;
import cn.myst.web.utils.RedisOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/8/23
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserTokenInterceptor implements HandlerInterceptor {
    private final RedisOperator redisOperator;

    static final String CHARACTER_ENCODING = "utf-8";
    static final String CONTENTTYPE_JSON = "application/json";

    /**
     * 返回异常信息Response
     */
    public void returnErrorResponse(HttpServletResponse response, IMOOCJSONResult result) {
        /*try (OutputStream outputStream = response.getOutputStream()) {
            response.setCharacterEncoding(CHARACTER_ENCODING);
            response.setContentType(CONTENTTYPE_JSON);
            outputStream.write(StringUtils.getBytes(JsonUtils.objectToJson(result), StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            byte[] bytes = JsonUtils.objectToJson(result).getBytes("utf-8");
            byte[] bytes1 = StringUtils.getBytes(JsonUtils.objectToJson(result), StandardCharsets.UTF_8);
            out.write(bytes1);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拦截请求（访问controller调用之前）
     * false：请求被拦截，被驳回，验证出现问题
     * true：请求在经过验证后，是OK的，可以放行的
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        String uniqueToken = redisOperator.get(EnumRedisKeys.USER_TOKEN.key + ":" + userId);
        // 判断用户是否登录
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(userToken) || StringUtils.isBlank(uniqueToken)) {
            this.returnErrorResponse(response, IMOOCJSONResult.errorMsg(EnumBaseException.PLEASE_LOGIN.zh));
            return false;
        }

        // 判断账号是否异地登录
        if (!Objects.equals(userToken, uniqueToken)) {
            this.returnErrorResponse(response, IMOOCJSONResult.errorMsg(EnumBaseException.REMOTE_LOGIN.zh));
            return false;
        }

        return true;
    }

    /**
     * 请求访问controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 请求访问controller之后，渲染视图之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
