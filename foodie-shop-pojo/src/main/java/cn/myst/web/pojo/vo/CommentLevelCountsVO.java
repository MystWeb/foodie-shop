package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/19
 * 商品评价数量VO
 */
@Data
@ApiModel(value = "商品评价数量VO")
public class CommentLevelCountsVO implements Serializable {

    @ApiModelProperty(value = "所有评价的数量")
    private Integer totalCounts;

    @ApiModelProperty(value = "好评的数量")
    private Integer goodCounts;

    @ApiModelProperty(value = "中评的数量")
    private Integer normalCounts;

    @ApiModelProperty(value = "差评的数量")
    private Integer badCounts;

    private static final long serialVersionUID = -3616983867576705847L;
}
