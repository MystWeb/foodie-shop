package cn.myst.web.controller.center;

import cn.myst.web.controller.BaseController;
import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumOrder;
import cn.myst.web.service.center.MyOrdersService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "商家发货", notes = "商家发货没有后端，所以这个接口仅仅只是用于模拟商家发货")
    @PutMapping("deliver")
    public IMOOCJSONResult deliver(
            @ApiParam(value = "订单id", required = true)
            @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户确认收货", notes = "用户确认收货")
    @PostMapping("confirmReceive")
    public IMOOCJSONResult deliver(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(value = "订单id", required = true)
            @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        IMOOCJSONResult checkResult = myOrdersService.checkUserOrder(userId, orderId);
        if (HttpStatus.OK.value() != checkResult.getStatus()) {
            return checkResult;
        }

        boolean result = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!result) {
            return IMOOCJSONResult.errorMsg(EnumOrder.ORDER_CONFIRMATION_RECEIPT_FAILED.zh);
        }
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单")
    @DeleteMapping("delete")
    public IMOOCJSONResult delete(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(value = "订单id", required = true)
            @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        IMOOCJSONResult checkResult = myOrdersService.checkUserOrder(userId, orderId);
        if (HttpStatus.OK.value() != checkResult.getStatus()) {
            return checkResult;
        }

        boolean result = myOrdersService.deleteOrder(userId, orderId);
        if (!result) {
            return IMOOCJSONResult.errorMsg(EnumOrder.ORDER_DELETE_FAILED.zh);
        }
        return IMOOCJSONResult.ok();
    }
}
