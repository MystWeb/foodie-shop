package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/11/1
 */
@AllArgsConstructor
@Getter
public enum EnumESHighlight {

    // 高亮前缀
    PRE_TAG("<font color='red'>"),
    // 高亮后缀
    POST_TAG("</font>"),

    ;

    public final String value;
}
