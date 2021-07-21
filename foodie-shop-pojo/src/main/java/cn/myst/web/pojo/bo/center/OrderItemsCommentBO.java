package cn.myst.web.pojo.bo.center;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/7/21
 * 业务层交互数据 用户中心-评论BO
 */
@ApiModel(value = "用户中心-评论BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class OrderItemsCommentBO implements Serializable {
    @JsonIgnore
    @ApiModelProperty(value = "商品评价id")
    private String commentId;

    @ApiModelProperty(value = "商品id")
    private String itemId;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品规格id")
    private String itemSpecId;

    @ApiModelProperty(value = "商品规格名称")
    private String itemSpecName;

    @ApiModelProperty(value = "评价等级")
    private Integer commentLevel;

    @ApiModelProperty(value = "商品内容")
    private String content;

    private static final long serialVersionUID = 5027520291867621781L;
}
