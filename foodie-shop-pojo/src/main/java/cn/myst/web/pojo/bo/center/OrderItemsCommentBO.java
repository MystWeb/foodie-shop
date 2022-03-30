package cn.myst.web.pojo.bo.center;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/7/21
 * 业务层交互数据 用户中心-评论BO
 */
@Schema(title = "用户中心-评论BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class OrderItemsCommentBO implements Serializable {
    @JsonIgnore
    @Schema(name = "商品评价id")
    private String commentId;

    @Schema(name = "商品id")
    private String itemId;

    @Schema(name = "商品名称")
    private String itemName;

    @Schema(name = "商品规格id")
    private String itemSpecId;

    @Schema(name = "商品规格名称")
    private String itemSpecName;

    @Schema(name = "评价等级")
    private Integer commentLevel;

    @Schema(name = "商品内容")
    private String content;

    private static final long serialVersionUID = 5027520291867621781L;
}
