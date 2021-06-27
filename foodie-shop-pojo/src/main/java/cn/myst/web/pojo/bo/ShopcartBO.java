package cn.myst.web.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/27
 * 业务层交互数据 购物车BO
 */
@ApiModel(value = "购物车BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class ShopcartBO implements Serializable {
    @ApiModelProperty(value = "商品id")
    private String itemId;

    @ApiModelProperty(value = "商品图片地址")
    private String itemImgUrl;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品规格id")
    private String specId;

    @ApiModelProperty(value = "商品规格名称")
    private String specName;

    @ApiModelProperty(value = "购买数量")
    private Integer buyCounts;

    @ApiModelProperty(value = "商品优惠价格")
    private String priceDiscount;

    @ApiModelProperty(value = "商品原价")
    private String priceNormal;

    private static final long serialVersionUID = -1123328802253574908L;
}
