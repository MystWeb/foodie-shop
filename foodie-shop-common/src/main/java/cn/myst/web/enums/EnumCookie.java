package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/7/16
 * Cookie 枚举
 */
@AllArgsConstructor
@Getter
public enum EnumCookie {
    USER("user"),
    SHOP_CART("shopcart"),
    USER_TICKET("userTicket"),

    ;
    public final String cookieName;

}
