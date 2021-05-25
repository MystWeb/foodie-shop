package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/5/20
 * 性别 枚举
 */
public enum EnumSex {
    WOMAN(0, "女"),
    MAN(1, "男"),
    SECRET(2, "保密");

    public final Integer type;
    public final String value;

    EnumSex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
