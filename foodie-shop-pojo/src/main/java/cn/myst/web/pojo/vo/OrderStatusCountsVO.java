package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/7/22
 */
@Schema(title = "订单状态概览数量VO")
@Data
@Builder
public class OrderStatusCountsVO implements Serializable {

    @Schema(name = "待付款订单数量")
    private Integer waitPayCounts;

    @Schema(name = "待发货订单数量")
    private Integer waitDeliverCounts;

    @Schema(name = "待收货订单数量")
    private Integer waitReceiveCounts;

    @Schema(name = "待评价订单数量")
    private Integer waitCommentCounts;

    private static final long serialVersionUID = -1767149166375539122L;
}
