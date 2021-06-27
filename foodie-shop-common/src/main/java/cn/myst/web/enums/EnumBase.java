package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/6/27
 */
public enum EnumBase {
    PARAMETER_CANNOT_BE_EMPTY("参数不能为空", "PARAMETER_CANNOT_BE_EMPTY"),

    ;

    public final String zh;
    public final String en;

    EnumBase(String zh, String en) {
        this.zh = zh;
        this.en = en;
    }
}
