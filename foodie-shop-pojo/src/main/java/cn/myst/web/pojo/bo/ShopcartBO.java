package cn.myst.web.pojo.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/27
 * 业务层交互数据 购物车BO
 */
@Schema(title = "购物车BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class ShopcartBO implements Serializable {
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

    @Schema(title = "购买数量")
    private Integer buyCounts;

    @Schema(title = "商品优惠价格")
    private String priceDiscount;

    @Schema(title = "商品原价")
    private String priceNormal;

    private static final long serialVersionUID = -1123328802253574908L;
}
