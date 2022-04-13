package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/20
 * 搜索商品列表VO
 */
@Data
@Schema(title = "搜索商品列表VO")
public class SearchItemsVO implements Serializable {
    @Schema(title = "商品id")
    private String itemId;

    @Schema(title = "商品名称")
    private String itemName;

    @Schema(title = "商品累计销量")
    private Integer sellCounts;

    @Schema(title = "商品图片")
    private String imgUrl;

    @Schema(title = "商品价格", description = "商品优惠价")
    private Integer price;

    private static final long serialVersionUID = -3566955103175469971L;
}
