package cn.myst.web.pojo.bo.center;

import cn.myst.web.constant.ConstantUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ziming.xing
 * Create Date：2021/7/16
 * 业务层交互数据 用户中心-用户BO
 */
@ApiModel(value = "用户中心-用户BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class CenterUserBO implements Serializable {
    @NotBlank(message = ConstantUser.USERNAME_IS_EMPTY)
    @ApiModelProperty(value = "用户名", example = "json")
    private String username;

    @NotBlank(message = ConstantUser.USER_NICKNAMES_CANNOT_BE_EMPTY)
    @Length(max = 12, message = ConstantUser.USER_NICKNAMES_CANNOT_EXCEED_12_DIGITS)
    @ApiModelProperty(value = "用户昵称", name = "nickname", example = "杰森")
    private String nickname;

    @Length(max = 12, message = ConstantUser.USER_REAL_NAME_CANNOT_EXCEED_12_CHARACTERS)
    @ApiModelProperty(value = "真实姓名", name = "realname", example = "杰森")
    private String realname;

    @Pattern(regexp = ConstantUser.USER_MOBILE_REGEXP, message = ConstantUser.FORMAT_OF_THE_PHONE_NUMBER_IS_NOT_CORRECT)
    @ApiModelProperty(value = "手机号", name = "mobile", example = "13999999999")
    private String mobile;

    @Email
    @ApiModelProperty(value = "邮箱地址", name = "email", example = "your_mail@mail.com")
    private String email;

    @Min(value = 0, message = ConstantUser.INCORRECT_SEX_SELECTION)
    @Max(value = 2, message = ConstantUser.INCORRECT_SEX_SELECTION)
    @ApiModelProperty(value = "性别", name = "sex", example = "0:女 1:男 2:保密")
    private Integer sex;

    @ApiModelProperty(value = "生日", name = "birthday", example = "1900-01-01")
    private Date birthday;

    private static final long serialVersionUID = -4095573424622051817L;
}
