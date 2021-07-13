package cn.myst.web.controller.center;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziming.xing
 * Create Date：2021/7/13
 */
@Api(value = "用户中心", tags = "用户中心的相关接口")
@RestController
@RequestMapping("/center")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CenterController {
}
