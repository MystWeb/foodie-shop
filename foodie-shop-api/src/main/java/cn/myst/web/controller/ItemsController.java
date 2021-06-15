package cn.myst.web.controller;

import cn.myst.web.pojo.Items;
import cn.myst.web.pojo.ItemsImg;
import cn.myst.web.pojo.ItemsParam;
import cn.myst.web.pojo.ItemsSpec;
import cn.myst.web.pojo.vo.ItemInfoVO;
import cn.myst.web.service.ItemService;
import cn.myst.web.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/6/15
 */
@Api(value = "商品", tags = "商品信息展示的相关接口")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemsController {

    private final ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "itemId", value = "商品id", readOnly = true)
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

}
