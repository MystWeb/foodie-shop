package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/8/20
 */
@Schema(title = "用户VO")
@Data
public class UsersVO implements Serializable {

    @Schema(name = "主键id")
    private String id;

    @Schema(name = "用户名")
    private String username;

    @Schema(name = "昵称")
    private String nickname;

    @Schema(name = "头像")
    private String face;

    @Schema(name = "性别 1:男  0:女  2:保密")
    private Integer sex;

    @Schema(name = "用户会话token")
    private String userUniqueToken;

    private static final long serialVersionUID = -1801442528862175994L;
}
