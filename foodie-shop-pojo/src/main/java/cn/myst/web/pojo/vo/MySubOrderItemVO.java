package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@Schema(title = "用户中心-我的订单列表嵌套商品VO")
@Data
public class MySubOrderItemVO implements Serializable {

    @Schema(name = "商品id")
    private String itemId;

    @Schema(name = "商品名称")
    private String itemName;

    @Schema(name = "商品图片")
    private String itemImg;

    @Schema(name = "商品规格id")
    private String itemSpecId;

    @Schema(name = "商品规格名称")
    private String itemSpecName;

    @Schema(name = "购买数量")
    private Integer buyCounts;

    @Schema(name = "成交价格")
    private Integer price;

    private static final long serialVersionUID = -1432326213105839720L;
}
