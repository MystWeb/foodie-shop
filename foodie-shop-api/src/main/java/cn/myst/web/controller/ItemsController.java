package cn.myst.web.controller;

import cn.myst.web.pojo.Items;
import cn.myst.web.pojo.ItemsImg;
import cn.myst.web.pojo.ItemsParam;
import cn.myst.web.pojo.ItemsSpec;
import cn.myst.web.pojo.vo.CommentLevelCountsVO;
import cn.myst.web.pojo.vo.ItemInfoVO;
import cn.myst.web.service.ItemService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "商品", tags = "商品信息展示的相关接口")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemsController extends BaseController {

    private final ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
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

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult getCommentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        CommentLevelCountsVO countsVO = itemService.queryCommentCountsByItemId(itemId);
        return IMOOCJSONResult.ok(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论")
    @GetMapping("/comments")
    public IMOOCJSONResult getComments(
            @ApiParam(value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(value = "评价等级")
            @RequestParam(required = false) Integer level,
            @ApiParam(value = "查询下一页的第几页")
            @RequestParam Integer page,
            @ApiParam(value = "分页的每一页显示的条数")
            @RequestParam Integer pageSize
    ) {
        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
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

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表")
    @GetMapping("/search")
    public IMOOCJSONResult getComments(
            @ApiParam(value = "关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(value = "排序")
            @RequestParam(required = false) String sort,
            @ApiParam(value = "查询下一页的第几页")
            @RequestParam Integer page,
            @ApiParam(value = "分页的每一页显示的条数")
            @RequestParam Integer pageSize
    ) {
        if (StringUtils.isBlank(keywords)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }

    @ApiOperation(value = "根据分类id搜索商品列表", notes = "根据分类id搜索商品列表")
    @GetMapping("/catItems")
    public IMOOCJSONResult catItems(
            @ApiParam(value = "三级分类id", required = true)
            @RequestParam Integer catId,
            @ApiParam(value = "排序")
            @RequestParam(required = false) String sort,
            @ApiParam(value = "查询下一页的第几页")
            @RequestParam Integer page,
            @ApiParam(value = "分页的每一页显示的条数")
            @RequestParam Integer pageSize
    ) {
        if (Objects.isNull(catId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searchItems(catId, sort, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }
}
