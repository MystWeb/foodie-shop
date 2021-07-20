package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@ApiModel(value = "用户中心-我的订单列表VO")
@Data
public class MyOrdersVO implements Serializable {

    @ApiModelProperty(value = "订单ID")
    private String orderId;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "支付方式")
    private Integer payMethod;

    @ApiModelProperty(value = "实际支付总价格")
    private Integer realPayAmount;

    @ApiModelProperty(value = "邮费")
    private Integer postAmount;

    @ApiModelProperty(value = "买家是否评价")
    private Integer isComment;

    @ApiModelProperty(value = "订单状态")
    private Integer orderStatus;

    @ApiModelProperty(value = "用户中心-我的订单列表嵌套商品VO")
    private List<MySubOrderItemVO> subOrderItemList;

    private static final long serialVersionUID = -8371659514007978640L;
}
