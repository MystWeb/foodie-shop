package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/5/28
 * 分类类型 枚举
 */
public enum EnumType {
    LARGE_CLASSIFICATION(1, "一级分类"),
    SMALL_CLASSIFICATION(2, "二级分类"),
    CHILD_CLASSIFICATION(3, "三级分类"),
    ;

    public final Integer type;
    public final String value;

    EnumType(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
