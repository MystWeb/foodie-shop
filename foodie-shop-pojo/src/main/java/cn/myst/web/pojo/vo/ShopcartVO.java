package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/27
 * 购物车VO
 */
@ApiModel(value = "购物车VO")
@Data
public class ShopcartVO implements Serializable {
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

    @ApiModelProperty(value = "商品优惠价格")
    private String priceDiscount;

    @ApiModelProperty(value = "商品原价")
    private String priceNormal;

    private static final long serialVersionUID = 896929880529714823L;
}
