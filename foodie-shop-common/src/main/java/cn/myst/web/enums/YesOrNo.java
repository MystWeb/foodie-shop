package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/5/20
 * 是否 枚举
 */
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是"),
    ;

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
