package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/27
 * 购物车VO
 */
@Schema(title = "购物车VO")
@Data
public class ShopcartVO implements Serializable {
    @Schema(title = "商品id")
    private String itemId;

    @Schema(title = "商品图片地址")
    private String itemImgUrl;

    @Schema(title = "商品名称")
    private String itemName;

    @Schema(title = "商品规格id")
    private String specId;

    @Schema(title = "商品规格名称")
    private String specName;

    @Schema(title = "商品优惠价格")
    private String priceDiscount;

    @Schema(title = "商品原价")
    private String priceNormal;

    private static final long serialVersionUID = 896929880529714823L;
}
