package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/8/20
 */
@ApiModel(value = "用户VO")
@Data
public class UsersVO implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String face;

    @ApiModelProperty(value = "性别 1:男  0:女  2:保密")
    private Integer sex;

    @ApiModelProperty(value = "用户会话token")
    private String userUniqueToken;

    private static final long serialVersionUID = -1801442528862175994L;
}
