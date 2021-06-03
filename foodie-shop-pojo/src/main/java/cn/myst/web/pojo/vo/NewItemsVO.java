package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/6/3
 * 最新商品VO
 */
@ApiModel(value = "最新商品VO")
@Data
public class NewItemsVO implements Serializable {
    @ApiModelProperty(value = "一级分类id")
    private Integer rootCatId;

    @ApiModelProperty(value = "一级分类名称")
    private String rootCatName;

    @ApiModelProperty(value = "口号")
    private String slogan;

    @ApiModelProperty(value = "分类图")
    private String catImage;

    @ApiModelProperty(value = "背景颜色")
    private String bgColor;

    @ApiModelProperty(value = "6个最新商品的简单数据列表")
    private List<SimpleItemVO> simpleItemList;

    private static final long serialVersionUID = -4637369354538528681L;
}
