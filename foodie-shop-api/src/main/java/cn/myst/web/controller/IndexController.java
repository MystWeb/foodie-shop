package cn.myst.web.controller;

import cn.myst.web.enums.EnumYesOrNo;
import cn.myst.web.pojo.Carousel;
import cn.myst.web.pojo.Category;
import cn.myst.web.pojo.vo.CategoryVO;
import cn.myst.web.pojo.vo.NewItemsVO;
import cn.myst.web.service.CarouselService;
import cn.myst.web.service.CategoryService;
import cn.myst.web.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/5/25
 */
@Api(value = "首页", tags = "首页展示的相关接口")
@RestController
@RequestMapping("/index")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class IndexController {
    private final CarouselService carouselService;
    private final CategoryService categoryService;

    public static final String CLASSIFICATION_NOT_EXIST = "分类不存在";

    @ApiOperation(value = "获取首页轮播图", notes = "获取首页轮播图列表")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {
        List<Carousel> list = carouselService.queryAll(EnumYesOrNo.YES.type);
        return IMOOCJSONResult.ok(list);
    }

    /**
     * 首页分类展示需求：
     * 1、第一次刷新主页查询大分类，渲染展示到首页
     * 2、如果鼠标移动到大分类，则加载子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类（一级）", notes = "获取商品分类（一级）")
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {
        List<Category> list = categoryService.queryAllRootLevelCat();
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", readOnly = true)
            @PathVariable Integer rootCatId) {
        if (Objects.isNull(rootCatId)) {
            return IMOOCJSONResult.errorMsg(CLASSIFICATION_NOT_EXIST);
        }
        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", readOnly = true)
            @PathVariable Integer rootCatId) {
        if (Objects.isNull(rootCatId)) {
            return IMOOCJSONResult.errorMsg(CLASSIFICATION_NOT_EXIST);
        }
        List<NewItemsVO> list = categoryService.getSixNewItemLazy(rootCatId);
        return IMOOCJSONResult.ok(list);
    }
}
