package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/7/12
 * List静态实用方法 枚举
 */
@AllArgsConstructor
@Getter
public enum EnumLists {
    LISTS_PARTITION_SIZE(500, "Lists.partition(LISTS_PAGE_SIZE).forEach(item -> mapper.updateBatchSelective(item));"),

    ;
    public final Integer pageSize;
    public final String nodes;

}
