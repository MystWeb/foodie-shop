package cn.myst.web.controller;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.pojo.Items;
import cn.myst.web.pojo.ItemsImg;
import cn.myst.web.pojo.ItemsParam;
import cn.myst.web.pojo.ItemsSpec;
import cn.myst.web.pojo.vo.CommentLevelCountsVO;
import cn.myst.web.pojo.vo.ItemInfoVO;
import cn.myst.web.pojo.vo.ShopcartVO;
import cn.myst.web.service.ItemService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.PagedGridResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/6/15
 */
@Tag(name = "商品", description = "商品信息展示的相关接口")
@RestController
@RequestMapping("items")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemsController extends BaseController {

    private final ItemService itemService;

    @Operation(summary = "查询商品详情", description = "查询商品详情")
    @GetMapping("info/{itemId}")
    public IMOOCJSONResult info(
            @Parameter(description = "商品id", required = true)
            @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        Items item = itemService.queryItemByItemId(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgListByItemId(itemId);
        List<ItemsSpec> itemSpecList = itemService.queryItemSpecListByItemId(itemId);
        ItemsParam itemParam = itemService.queryItemParamByItemId(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemSpecList);
        itemInfoVO.setItemParams(itemParam);
        return IMOOCJSONResult.ok(itemInfoVO);
    }

    @Operation(summary = "查询商品评价等级", description = "查询商品评价等级")
    @GetMapping("commentLevel")
    public IMOOCJSONResult getCommentLevel(
            @Parameter(description = "商品id", required = true)
            @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        CommentLevelCountsVO countsVO = itemService.queryCommentCountsByItemId(itemId);
        return IMOOCJSONResult.ok(countsVO);
    }

    @Operation(summary = "查询商品评论", description = "查询商品评论")
    @GetMapping("comments")
    public IMOOCJSONResult getComments(
            @Parameter(description = "商品id", required = true)
            @RequestParam String itemId,
            @Parameter(description = "评价等级")
            @RequestParam(required = false) Integer level,
            @Parameter(description = "查询下一页的第几页")
            @RequestParam Integer page,
            @Parameter(description = "分页的每一页显示的条数")
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        if (page == null) {
            page = PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }

    @Operation(summary = "搜索商品列表", description = "搜索商品列表")
    @GetMapping("search")
    public IMOOCJSONResult search(
            @Parameter(description = "关键字", required = true)
            @RequestParam String keywords,
            @Parameter(description = "排序")
            @RequestParam(required = false) String sort,
            @Parameter(description = "查询下一页的第几页")
            @RequestParam Integer page,
            @Parameter(description = "分页的每一页显示的条数")
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        if (page == null) {
            page = PAGE;
        }

        if (pageSize == null) {
            pageSize = ITEMS_PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }

    @Operation(summary = "根据分类id搜索商品列表", description = "根据分类id搜索商品列表")
    @GetMapping("catItems")
    public IMOOCJSONResult catItems(
            @Parameter(description = "三级分类id", required = true)
            @RequestParam Integer catId,
            @Parameter(description = "排序")
            @RequestParam(required = false) String sort,
            @Parameter(description = "查询下一页的第几页")
            @RequestParam Integer page,
            @Parameter(description = "分页的每一页显示的条数")
            @RequestParam Integer pageSize) {
        if (Objects.isNull(catId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        if (page == null) {
            page = PAGE;
        }

        if (pageSize == null) {
            pageSize = ITEMS_PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searchItems(catId, sort, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }

    @Operation(summary = "根据商品规格ids查找最新的商品数据", description = "用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格）类似京东淘宝")
    @GetMapping("refresh")
    public IMOOCJSONResult refresh(
            @Parameter(description = "拼接的规格ids", example = "1001,1003,1005", required = true)
            @RequestParam String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return IMOOCJSONResult.ok();
        }
        List<ShopcartVO> list = itemService.queryItemsBySpecIds(itemSpecIds);
        return IMOOCJSONResult.ok(list);
    }
}
