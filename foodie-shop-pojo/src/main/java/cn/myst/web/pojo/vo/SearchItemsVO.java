package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/20
 * 搜索商品列表VO
 */
@Data
@ApiModel(value = "搜索商品列表VO")
public class SearchItemsVO implements Serializable {
    @ApiModelProperty(value = "商品id")
    private String itemId;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品累计销量")
    private Integer sellCounts;

    @ApiModelProperty(value = "商品图片")
    private String imgUrl;

    @ApiModelProperty(value = "商品价格", notes = "商品优惠价")
    private Integer price;

    private static final long serialVersionUID = -3566955103175469971L;
}
