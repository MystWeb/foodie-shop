package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/8/10
 */
@AllArgsConstructor
@Getter
public enum EnumRedisKeys {
    CAROUSEL("carousel", 5 * 60L, "首页轮播图"),
    CAT("cat", 5 * 60L, "商品分类（一级）"),
    SUB_CAT("subCat", 5 * 60L, "商品子分类"),
    SHOP_CART("shopcart", 5 * 60L, "购物车"),
    USER_TOKEN("userToken", 5 * 60L, "用户令牌"),

    ;

    public final String key;
    public final Long cacheTime;
    public final String notes;

}
