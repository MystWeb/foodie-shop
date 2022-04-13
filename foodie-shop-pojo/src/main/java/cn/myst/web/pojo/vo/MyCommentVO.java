package cn.myst.web.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ziming.xing
 * Create Date：2021/7/22
 */
@Schema(title = "用户中心-我的评价VO")
@Data
public class MyCommentVO implements Serializable {
    @JsonIgnore
    @Schema(title = "商品评价id")
    private String commentId;

    @Schema(title = "商品内容")
    private String content;

    @Schema(title = "创建时间")
    private Date createdTime;

    @Schema(title = "商品id")
    private String itemId;

    @Schema(title = "商品名称")
    private String itemName;

    @Schema(title = "商品规格id")
    private String itemSpecId;

    @Schema(title = "商品规格名称")
    private String specName;

    @Schema(title = "商品图片")
    private String itemImg;

    private static final long serialVersionUID = 7269040096062153484L;
}
