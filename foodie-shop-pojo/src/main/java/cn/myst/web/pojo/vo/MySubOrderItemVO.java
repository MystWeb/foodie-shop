package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@ApiModel(value = "用户中心-我的订单列表嵌套商品VO")
@Data
public class MySubOrderItemVO implements Serializable {

    @ApiModelProperty(value = "商品id")
    private String itemId;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品图片")
    private String itemImg;

    @ApiModelProperty(value = "商品规格id")
    private String itemSpecId;

    @ApiModelProperty(value = "商品规格名称")
    private String itemSpecName;

    @ApiModelProperty(value = "购买数量")
    private Integer buyCounts;

    @ApiModelProperty(value = "成交价格")
    private Integer price;

    private static final long serialVersionUID = -1432326213105839720L;
}
