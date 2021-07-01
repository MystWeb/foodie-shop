package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 */
public enum EnumOrder {
    PAYMENT_METHOD_IS_NOT_SUPPORTED("支付方式不支持！", "PAYMENT_METHOD_IS_NOT_SUPPORTED"),
    ORDER_CREATION_FAILED_BY_INSUFFICIENT_INVENTORY("由于库存不足导致订单创建失败！", "ORDER_CREATION_FAILED_BY_INSUFFICIENT_INVENTORY"),

    ;

    public final String zh;
    public final String en;

    EnumOrder(String zh, String en) {
        this.zh = zh;
        this.en = en;
    }
}
