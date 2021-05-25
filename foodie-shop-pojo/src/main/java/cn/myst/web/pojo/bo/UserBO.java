package cn.myst.web.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/5/20
 */
@ApiModel(value = "用户BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class UserBO implements Serializable {
    private static final long serialVersionUID = -1159689849586717724L;

    @ApiModelProperty(value = "用户名", example = "jack", required = true)
    private String username;

    @ApiModelProperty(value = "密码", example = "123456", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码", example = "123456")
    private String confirmPassword;

}
