package cn.myst.web.constant;

/**
 * @author ziming.xing
 * Create Date：2021/7/16
 */
public interface ConstantUser {
    String USER_REGISTER_INFO_EMPTY = "用户注册信息不能为空";
    String USERNAME_IS_EMPTY = "用户名不能为空";
    String USERNAME_PASSWORD_IS_EMPTY = "用户名或密码不能为空";
    String USERNAME_ALREADY_EXISTS = "用户名已经存在";
    String PASSWORD_LENGTH_ERROR = "密码长度不能少于6位数或大于20位数";
    String INCONSISTENT_PASSWORDS = "两次密码输入不一致";
    String INCORRECT_USERNAME_OR_PASSWORD = "用户名或密码错误";
    String USER_NICKNAMES_CANNOT_BE_EMPTY = "用户昵称不能为空";
    String USER_NICKNAMES_CANNOT_EXCEED_12_DIGITS = "用户昵称不能超过12位";
    String USER_REAL_NAME_CANNOT_EXCEED_12_CHARACTERS = "用户真实姓名不能超过12位";
    String FORMAT_OF_THE_PHONE_NUMBER_IS_NOT_CORRECT = "手机号格式不正确";
    String INCORRECT_SEX_SELECTION = "性别选择不正确";

    String USER_MOBILE_REGEXP = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$";
}
