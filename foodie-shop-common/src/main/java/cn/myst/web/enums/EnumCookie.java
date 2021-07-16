package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/7/16
 * Cookie 枚举
 */
public enum EnumCookie {
    USER("user"),

    ;
    public final String cookieName;

    EnumCookie(String cookieName) {
        this.cookieName = cookieName;
    }
}
