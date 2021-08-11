package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/6/19
 * 商品评价等级 枚举
 */
@AllArgsConstructor
@Getter
public enum EnumCommentLevel {
    GOOD(1, "好评"),
    NORMAL(2, "中评"),
    BAD(3, "差评"),
    ;

    public final Integer type;
    public final String value;

}
