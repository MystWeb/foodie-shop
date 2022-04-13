package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@Schema(title = "用户中心-我的订单列表VO")
@Data
public class MyOrdersVO implements Serializable {

    @Schema(title = "订单ID")
    private String orderId;

    @Schema(title = "创建时间")
    private Date createdTime;

    @Schema(title = "支付方式")
    private Integer payMethod;

    @Schema(title = "实际支付总价格")
    private Integer realPayAmount;

    @Schema(title = "邮费")
    private Integer postAmount;

    @Schema(title = "买家是否评价")
    private Integer isComment;

    @Schema(title = "订单状态")
    private Integer orderStatus;

    @Schema(title = "用户中心-我的订单列表嵌套商品VO")
    private List<MySubOrderItemVO> subOrderItemList;

    private static final long serialVersionUID = -8371659514007978640L;
}
