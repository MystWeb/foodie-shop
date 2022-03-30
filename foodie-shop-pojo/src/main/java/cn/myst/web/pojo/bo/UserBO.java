package cn.myst.web.pojo.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/5/20
 * 业务层交互数据 用户BO
 */
@Schema(title = "用户BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class UserBO implements Serializable {
    private static final long serialVersionUID = -1159689849586717724L;

    @Schema(name = "用户名", example = "jack", required = true)
    private String username;

    @Schema(name = "密码", example = "123456", required = true)
    private String password;

    @Schema(name = "确认密码", example = "123456")
    private String confirmPassword;

}
