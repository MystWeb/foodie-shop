package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/6/29
 * 地址校验 枚举
 */
@AllArgsConstructor
@Getter
public enum EnumUserValidation {
    USER_REGISTER_INFO_EMPTY("用户注册信息不能为空", "USER_REGISTER_INFO_EMPTY"),
    USERNAME_IS_EMPTY("用户名不能为空", "USERNAME_IS_EMPTY"),
    USERNAME_PASSWORD_IS_EMPTY("用户名或密码不能为空", "USERNAME_PASSWORD_IS_EMPTY"),
    USERNAME_ALREADY_EXISTS("用户名已经存在", "USERNAME_ALREADY_EXISTS"),
    PASSWORD_LENGTH_ERROR("密码长度不能少于6位数或大于20位数", "PASSWORD_LENGTH_ERROR"),
    INCONSISTENT_PASSWORDS("两次密码输入不一致", "INCONSISTENT_PASSWORDS"),
    INCORRECT_USERNAME_OR_PASSWORD("用户名或密码错误", "INCORRECT_USERNAME_OR_PASSWORD"),

    ;

    public final String zh;
    public final String en;

}
