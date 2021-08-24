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
    USER_TICKET("userTicket", 5 * 60L, "用户全局门票"),
    TMP_TICKET("tmpTicket", 10 * 60L, "临时门票"),

    ;

    public final String key;
    public final Long cacheTime;
    public final String notes;

}
