package cn.myst.web.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ziming.xing
 * Create Date：2021/7/22
 */
@ApiModel(value = "用户中心-我的评价VO")
@Data
public class MyCommentVO implements Serializable {
    @JsonIgnore
    @ApiModelProperty(value = "商品评价id")
    private String commentId;

    @ApiModelProperty(value = "商品内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "商品id")
    private String itemId;

    @ApiModelProperty(value = "商品名称")
    private String itemName;

    @ApiModelProperty(value = "商品规格id")
    private String itemSpecId;

    @ApiModelProperty(value = "商品规格名称")
    private String specName;

    @ApiModelProperty(value = "商品图片")
    private String itemImg;

    private static final long serialVersionUID = 7269040096062153484L;
}
