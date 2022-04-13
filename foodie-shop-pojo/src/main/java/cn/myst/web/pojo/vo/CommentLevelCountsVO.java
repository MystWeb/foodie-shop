package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/19
 * 商品评价数量VO
 */
@Data
@Schema(title = "商品评价数量VO")
public class CommentLevelCountsVO implements Serializable {

    @Schema(title = "所有评价的数量")
    private Long totalCounts;

    @Schema(title = "好评的数量")
    private Long goodCounts;

    @Schema(title = "中评的数量")
    private Long normalCounts;

    @Schema(title = "差评的数量")
    private Long badCounts;

    private static final long serialVersionUID = -3616983867576705847L;
}
