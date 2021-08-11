package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/5/20
 * 是否 枚举
 */
@AllArgsConstructor
@Getter
public enum EnumYesOrNo {
    NO(0, "否"),
    YES(1, "是"),
    ;

    public final Integer type;
    public final String value;

}
