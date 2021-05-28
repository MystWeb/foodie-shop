package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/5/28
 * 数据层交互数据 三级分类VO
 */
@ApiModel(value = "三级分类VO", description = "数据库查询出的数据封装在此entity中，用于展示层")
@Data
public class SubCategoryVO implements Serializable {
    private static final long serialVersionUID = 2628874213436335680L;

    @ApiModelProperty(value = "三级分类主键")
    private Integer subId;

    @ApiModelProperty(value = "三级分类名称")
    private String subName;

    @ApiModelProperty(value = "三级分类类型")
    private Integer subType;

    @ApiModelProperty(value = "三级分类父id")
    private Integer subFatherId;
}
