package cn.myst.web.controller;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumPage;
import cn.myst.web.service.ItemsESService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.PagedGridResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "商品", description = "商品信息展示的相关接口")
@RestController
@RequestMapping("items")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemsController {

    private final ItemsESService itemsESService;

    @Operation(summary = "搜索商品列表", description = "搜索商品列表")
    @GetMapping("/es/search")
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
            page = EnumPage.ES_PAGE.getValue();
        }

        if (pageSize == null) {
            pageSize = EnumPage.ES_ITEMS_PAGE_SIZE.getValue();
        }

        PagedGridResult grid = itemsESService.searchItems(keywords, sort, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }
}
