package cn.myst.web.controller;

import cn.myst.web.enums.EnumRedisKeys;
import cn.myst.web.enums.EnumYesOrNo;
import cn.myst.web.pojo.Carousel;
import cn.myst.web.pojo.Category;
import cn.myst.web.pojo.vo.CategoryVO;
import cn.myst.web.pojo.vo.NewItemsVO;
import cn.myst.web.service.CarouselService;
import cn.myst.web.service.CategoryService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.JsonUtils;
import cn.myst.web.utils.RedisOperator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tika.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
@Tag(name = "首页", description = "首页展示的相关接口")
@RestController
@RequestMapping("index")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class IndexController {
    public static final String CLASSIFICATION_NOT_EXIST = "分类不存在";

    private final CarouselService carouselService;
    private final CategoryService categoryService;
    private final RedisOperator redisOperator;

    /**
     * 1. 后台运营系统，一旦广告（轮播图）发生更改，就可以删除缓存，然后重置
     * 2. 定时重置，比如每天凌晨三点重置
     * 3. 每个轮播图都有可能是一个广告，每个广告都会有一个过期时间，过期了，再重置
     */
    @Operation(summary = "获取首页轮播图", description = "获取首页轮播图列表")
    @GetMapping("carousel")
    public IMOOCJSONResult carousel() {
        List<Carousel> list;
        String json = redisOperator.get(EnumRedisKeys.CAROUSEL.key);
        if (StringUtils.isBlank(json)) {
            list = carouselService.queryAll(EnumYesOrNo.YES.type);
            redisOperator.set(EnumRedisKeys.CAROUSEL.key, JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(json, Carousel.class);
        }
        return IMOOCJSONResult.ok(list);
    }

    /**
     * 首页分类展示需求：
     * 1、第一次刷新主页查询大分类，渲染展示到首页
     * 2、如果鼠标移动到大分类，则加载子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @Operation(summary = "获取商品分类（一级）", description = "获取商品分类（一级）")
    @GetMapping("cats")
    public IMOOCJSONResult cats() {
        List<Category> list;
        String json = redisOperator.get(EnumRedisKeys.CAT.key);
        if (StringUtils.isBlank(json)) {
            list = categoryService.queryAllRootLevelCat();
            redisOperator.set(EnumRedisKeys.CAT.key, JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(json, Category.class);
        }
        return IMOOCJSONResult.ok(list);
    }

    @Operation(summary = "获取商品子分类", description = "获取商品子分类")
    @GetMapping("subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @Parameter(description = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (Objects.isNull(rootCatId)) {
            return IMOOCJSONResult.errorMsg(CLASSIFICATION_NOT_EXIST);
        }
        List<CategoryVO> list;
        String subCatKey = EnumRedisKeys.SUB_CAT.key + ":" + rootCatId;
        String json = redisOperator.get(subCatKey);
        if (StringUtils.isBlank(json)) {
            list = categoryService.querySubCatByRootCatId(rootCatId);
            /*
             * 查询的key在redis中不存在，
             * 对应的id在数据库也不存在，
             * 此时被非法用户进行攻击，大量的请求会直接打在db上，
             * 造成宕机，从而影响整个系统，
             * 这种现象称之为缓存穿透。
             * 解决方案：把空的数据也缓存起来，比如空字符串，空对象，空数组或list
             */
            if (CollectionUtils.isEmpty(list)) {
                redisOperator.set(subCatKey, JsonUtils.objectToJson(list), EnumRedisKeys.SUB_CAT.cacheTime);
            } else {
                redisOperator.set(subCatKey, JsonUtils.objectToJson(list));
            }
        } else {
            list = JsonUtils.jsonToList(json, CategoryVO.class);
        }
        return IMOOCJSONResult.ok(list);
    }

    @Operation(summary = "查询每个一级分类下的最新6条商品数据", description = "查询每个一级分类下的最新6条商品数据")
    @GetMapping("sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @Parameter(description = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (Objects.isNull(rootCatId)) {
            return IMOOCJSONResult.errorMsg(CLASSIFICATION_NOT_EXIST);
        }
        List<NewItemsVO> list = categoryService.querySixNewItemLazyByRootCatId(rootCatId);
        return IMOOCJSONResult.ok(list);
    }
}
