package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/5/20
 * 性别 枚举
 */
@AllArgsConstructor
@Getter
public enum EnumSex {
    WOMAN(0, "女"),
    MAN(1, "男"),
    SECRET(2, "保密"),
    ;

    public final Integer type;
    public final String value;

}
