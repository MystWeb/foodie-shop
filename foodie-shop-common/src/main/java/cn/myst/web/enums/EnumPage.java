package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/10/30
 */
@AllArgsConstructor
@Getter
public enum EnumPage {
    // ES分页从第0页开始
    ES_PAGE(0),
    ES_ITEMS_PAGE_SIZE(20),
    ;

    public final Integer value;
}
