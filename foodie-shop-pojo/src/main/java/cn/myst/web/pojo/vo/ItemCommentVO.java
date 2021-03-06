package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ziming.xing
 * Create Date：2021/6/19
 * 商品评价VO
 */
@Data
@Schema(title = "商品评价VO")
public class ItemCommentVO implements Serializable {
    @Schema(title = "评价等级 1：好评 2：中评 3：差评")
    private Integer commentLevel;

    @Schema(title = "商品内容")
    private String content;

    @Schema(title = "规格名称 可为空")
    private String sepcName;

    @Schema(title = "创建时间")
    private Date createdTime;

    @Schema(title = "用户头像")
    private String userFace;

    @Schema(title = "昵称")
    private String nickname;

    private static final long serialVersionUID = -6867727097351956826L;
}
