package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/7/22
 */
@ApiModel(value = "订单状态概览数量VO")
@Data
@Builder
public class OrderStatusCountsVO implements Serializable {

    @ApiModelProperty(value = "待付款订单数量")
    private Integer waitPayCounts;

    @ApiModelProperty(value = "待发货订单数量")
    private Integer waitDeliverCounts;

    @ApiModelProperty(value = "待收货订单数量")
    private Integer waitReceiveCounts;

    @ApiModelProperty(value = "待评价订单数量")
    private Integer waitCommentCounts;

    private static final long serialVersionUID = -1767149166375539122L;
}
