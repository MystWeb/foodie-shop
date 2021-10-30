package cn.myst.web.controller;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumPage;
import cn.myst.web.service.ItemsESService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziming.xing
 * Create Date：2021/10/30
 */
@Api(value = "商品", tags = "商品信息展示的相关接口")
@RestController
@RequestMapping("items")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemsController {

    private final ItemsESService itemsESService;

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表")
    @GetMapping("/es/search")
    public IMOOCJSONResult search(
            @ApiParam(value = "关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(value = "排序")
            @RequestParam(required = false) String sort,
            @ApiParam(value = "查询下一页的第几页")
            @RequestParam Integer page,
            @ApiParam(value = "分页的每一页显示的条数")
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        if (page == null) {
            page = EnumPage.ES_PAGE.getValue();
        }

        if (pageSize == null) {
            pageSize = EnumPage.ES_ITEMS_PAGE_SIZE.getValue();
        }

        PagedGridResult grid = itemsESService.searchItems(keywords, sort, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }
}
