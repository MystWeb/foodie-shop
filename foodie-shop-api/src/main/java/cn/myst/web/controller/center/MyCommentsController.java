package cn.myst.web.controller.center;

import cn.myst.web.controller.BaseController;
import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.enums.EnumOrder;
import cn.myst.web.enums.EnumYesOrNo;
import cn.myst.web.pojo.OrderItems;
import cn.myst.web.pojo.Orders;
import cn.myst.web.pojo.bo.center.OrderItemsCommentBO;
import cn.myst.web.service.center.MyCommentsService;
import cn.myst.web.service.center.MyOrdersService;
import cn.myst.web.utils.IMOOCJSONResult;
import cn.myst.web.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/7/21
 */
@Slf4j
@Api(value = "用户中心-评价模块", tags = "用户中心-评价模块的相关接口")
@RestController
@RequestMapping("mycomments")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyCommentsController extends BaseController {
    private final MyCommentsService myCommentsService;
    private final MyOrdersService myOrdersService;

    @ApiOperation(value = "查询评论列表")
    @PostMapping("pending")
    public IMOOCJSONResult pending(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(value = "订单id", required = true)
            @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        // 判断户和订单是否有关联关系
        IMOOCJSONResult checkResult = myOrdersService.checkUserOrder(userId, orderId);
        if (HttpStatus.OK.value() != checkResult.getStatus()) {
            return checkResult;
        }

        // 判断该笔订单是否已经评价过，评价过了就不再继续
        Orders myOrder = null;
        if (checkResult.getData() instanceof Orders) {
            myOrder = (Orders) checkResult.getData();
        }
        if (Objects.nonNull(myOrder) && Objects.equals(EnumYesOrNo.YES.type, myOrder.getIsComment())) {
            return IMOOCJSONResult.errorMsg(EnumOrder.ORDER_HAS_BEEN_EVALUATED.zh);
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "保存评论列表")
    @PostMapping("saveList")
    public IMOOCJSONResult saveList(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(value = "用户中心-评论BO")
            @RequestBody List<OrderItemsCommentBO> commentList) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId) || CollectionUtils.isEmpty(commentList)) {
            return IMOOCJSONResult.errorMsg(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        log.debug(String.valueOf(commentList));
        // 判断户和订单是否有关联关系
        IMOOCJSONResult checkResult = myOrdersService.checkUserOrder(userId, orderId);
        if (HttpStatus.OK.value() != checkResult.getStatus()) {
            return checkResult;
        }
        myCommentsService.saveComments(userId, orderId, commentList);
        return IMOOCJSONResult.ok();
    }


    @ApiOperation(value = "查询我的评价列表", notes = "由于是用户中心的API，为了安全起见，查询方法请求方式使用POST")
    @PostMapping("query")
    public IMOOCJSONResult query(
            @ApiParam(value = "用户id", required = true)
            @RequestParam String userId,
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
        PagedGridResult grid = myCommentsService.queryMyComments(userId, page, pageSize);
        return IMOOCJSONResult.ok(grid);
    }

}
