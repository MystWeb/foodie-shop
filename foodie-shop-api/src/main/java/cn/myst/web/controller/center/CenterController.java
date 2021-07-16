package cn.myst.web.controller.center;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.pojo.Users;
import cn.myst.web.service.center.CenterUserService;
import cn.myst.web.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziming.xing
 * Create Date：2021/7/13
 */
@Api(value = "用户中心", tags = "用户中心的相关接口")
@RestController
@RequestMapping("center")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CenterController {
    private final CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    @GetMapping("userInfo")
    public IMOOCJSONResult userInfo(
            @ApiParam(value = "用户id", required = true)
                    String userId) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        Users users = centerUserService.queryUserInfo(userId);
        return IMOOCJSONResult.ok(users);
    }
}
