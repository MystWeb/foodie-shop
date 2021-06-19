package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ziming.xing
 * Create Date：2021/6/19
 * 商品评价VO
 */
@Data
@ApiModel(value = "商品评价VO")
public class ItemCommentVO implements Serializable {
    @ApiModelProperty(value = "评价等级 1：好评 2：中评 3：差评")
    private Integer commentLevel;

    @ApiModelProperty(value = "商品内容")
    private String content;

    @ApiModelProperty(value = "规格名称 可为空")
    private String sepcName;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "用户头像")
    private String userFace;

    @ApiModelProperty(value = "昵称")
    private String nickname;


    private static final long serialVersionUID = -6867727097351956826L;
}
