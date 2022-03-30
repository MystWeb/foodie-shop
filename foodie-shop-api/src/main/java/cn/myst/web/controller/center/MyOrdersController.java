package cn.myst.web.controller.center;

import cn.myst.web.controller.BaseController;
import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumOrder;
import cn.myst.web.pojo.vo.OrderStatusCountsVO;
import cn.myst.web.service.center.MyOrdersService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.PagedGridResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@Tag(name = "用户中心-我的订单", description = "用户中心-我的订单的相关接口")
@RestController
@RequestMapping("myorders")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyOrdersController extends BaseController {
    private final MyOrdersService myOrdersService;

    @Operation(summary = "查询我的订单列表", description = "由于是用户中心的API，为了安全起见，查询方法请求方式使用POST")
    @PostMapping("query")
    public IMOOCJSONResult queryMyOrders(
            @Parameter(description = "用户id", required = true)
            @RequestParam String userId,
            @Parameter(description = "订单状态")
            @RequestParam(required = false) Integer orderStatus,
            @Parameter(description = "查询下一页的第几页")
            @RequestParam(required = false) Integer page,
            @Parameter(description = "分页的每一页显示的条数")
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

    @Operation(summary = "商家发货", description = "商家发货没有后端，所以这个接口仅仅只是用于模拟商家发货")
    @PutMapping("deliver")
    public IMOOCJSONResult deliver(
            @Parameter(description = "订单id", required = true)
            @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }

    @Operation(summary = "用户确认收货", description = "用户确认收货")
    @PostMapping("confirmReceive")
    public IMOOCJSONResult deliver(
            @Parameter(description = "用户id", required = true)
            @RequestParam String userId,
            @Parameter(description = "订单id", required = true)
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

    @Operation(summary = "用户删除订单", description = "用户删除订单")
    @DeleteMapping("delete")
    public IMOOCJSONResult delete(
            @Parameter(description = "用户id", required = true)
            @RequestParam String userId,
            @Parameter(description = "订单id", required = true)
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

    @Operation(summary = "获得订单状态数概况", description = "由于是用户中心的API，为了安全起见，查询方法请求方式使用POST")
    @PostMapping("statusCounts")
    public IMOOCJSONResult statusCounts(
            @Parameter(description = "用户id", required = true)
            @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        OrderStatusCountsVO orderStatusCounts = myOrdersService.getOrderStatusCounts(userId);
        return IMOOCJSONResult.ok(orderStatusCounts);
    }

    @Operation(summary = "查询订单动向", description = "由于是用户中心的API，为了安全起见，查询方法请求方式使用POST")
    @PostMapping("trend")
    public IMOOCJSONResult trend(
            @Parameter(description = "用户id", required = true)
            @RequestParam String userId,
            @Parameter(description = "查询下一页的第几页")
            @RequestParam(required = false) Integer page,
            @Parameter(description = "分页的每一页显示的条数")
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
        PagedGridResult grid = myOrdersService.getMyOrderTrend(userId, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }
}
