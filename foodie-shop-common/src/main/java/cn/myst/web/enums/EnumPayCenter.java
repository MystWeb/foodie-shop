package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/7/12
 * 支付中心 枚举
 */
public enum EnumPayCenter {
    // 支付中心-认证账号
    USERNAME("imoocUserId", "imooc"),
    // 支付中心-认证密码
    PASSWORD("password", "imooc"),

    ;

    public final String headerName;
    public final String headerValue;

    EnumPayCenter(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }
}
