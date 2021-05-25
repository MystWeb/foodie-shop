package cn.myst.web.controller;

import cn.myst.web.enums.YesOrNo;
import cn.myst.web.pojo.Carousel;
import cn.myst.web.service.CarouselService;
import cn.myst.web.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/5/25
 */
@Api(value = "首页", tags = "首页展示的相关接口")
@RestController
@RequestMapping("/index")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CarouseController {
    private final CarouselService carouselService;

    @ApiOperation(value = "获取首页轮播图", notes = "获取首页轮播图列表")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {
        List<Carousel> carouselList = carouselService.queryAll(YesOrNo.YES.type);
        return IMOOCJSONResult.ok(carouselList);
    }
}
