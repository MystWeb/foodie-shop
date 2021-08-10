package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/8/10
 */
public enum EnumRedisKeys {
    CAROUSEL("carousel", 5 * 60L, "首页轮播图"),
    CAT("cat", 5 * 60L, "商品分类（一级）"),
    SUB_CAT("subCat", 5 * 60L, "商品子分类"),

    ;

    public final String key;
    public final Long cacheTime;
    public final String notes;

    EnumRedisKeys(String key, Long cacheTime, String notes) {
        this.key = key;
        this.cacheTime = cacheTime;
        this.notes = notes;
    }
}
