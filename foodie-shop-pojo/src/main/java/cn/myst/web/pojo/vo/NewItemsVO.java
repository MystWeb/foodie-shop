package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/6/3
 * 最新商品VO
 */
@Schema(title = "最新商品VO")
@Data
public class NewItemsVO implements Serializable {
    @Schema(title = "一级分类id")
    private Integer rootCatId;

    @Schema(title = "一级分类名称")
    private String rootCatName;

    @Schema(title = "口号")
    private String slogan;

    @Schema(title = "分类图")
    private String catImage;

    @Schema(title = "背景颜色")
    private String bgColor;

    @Schema(title = "6个最新商品的简单数据列表")
    private List<SimpleItemVO> simpleItemList;

    private static final long serialVersionUID = -4637369354538528681L;
}
