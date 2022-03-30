package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/5/28
 * 数据层交互数据 二级分类VO
 */
@Schema(title = "二级分类VO", description = "数据库查询出的数据封装在此entity中，用于展示层")
@Data
public class CategoryVO implements Serializable {
    private static final long serialVersionUID = 2628874213436335680L;

    @Schema(name = "主键")
    private Integer id;

    @Schema(name = "分类名称")
    private String name;

    @Schema(name = "分类类型")
    private Integer type;

    @Schema(name = "父id")
    private Integer fatherId;

    /**
     * 三级分类VO List
     */
    @Schema(name = "三级分类VO List")
    private List<SubCategoryVO> subCatList;

}
