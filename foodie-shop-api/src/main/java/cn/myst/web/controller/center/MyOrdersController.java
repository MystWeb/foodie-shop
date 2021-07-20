package cn.myst.web.controller.center;

import cn.myst.web.controller.BaseController;
import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.service.center.MyOrdersService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@Api(value = "用户中心-我的订单", tags = "用户中心-我的订单的相关接口")
@RestController
@RequestMapping("myorders")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyOrdersController extends BaseController {
    private final MyOrdersService myOrdersService;

    @ApiOperation(value = "查询我的订单列表", notes = "由于是用户中心的API，为了安全起见，查询方法请求方式使用POST")
    @PostMapping("query")
    public IMOOCJSONResult queryMyOrders(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(value = "订单状态")
            @RequestParam(required = false) Integer orderStatus,
            @ApiParam(value = "查询下一页的第几页")
            @RequestParam(required = false) Integer page,
            @ApiParam(value = "分页的每一页显示的条数")
            @RequestParam(required = false) Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        if (page == null) {
            page = PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult grid = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }

}
