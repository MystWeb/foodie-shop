package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 * 订单 枚举
 */
public enum EnumOrder {
    PAYMENT_METHOD_IS_NOT_SUPPORTED("支付方式不支持！", "PAYMENT_METHOD_IS_NOT_SUPPORTED"),
    ORDER_CREATION_FAILED_BY_INSUFFICIENT_INVENTORY("由于库存不足导致订单创建失败！", "ORDER_CREATION_FAILED_BY_INSUFFICIENT_INVENTORY"),
    ORDER_DOES_NOT_EXIST("订单不存在！", "ORDER_DOES_NOT_EXIST"),
    ORDER_CONFIRMATION_RECEIPT_FAILED("订单确认收货失败！", "ORDER_CONFIRMATION_RECEIPT_FAILED"),
    ORDER_DELETE_FAILED("订单删除失败！", "ORDER_DELETE_FAILED"),
    ORDER_HAS_BEEN_EVALUATED("该笔订单已经评价！", "ORDER_HAS_BEEN_EVALUATED"),
    SHOPPING_CART_DATA_ERROR("购物车数据错误！", "SHOPPING_CART_DATA_ERROR"),

    ;

    public final String zh;
    public final String en;

    EnumOrder(String zh, String en) {
        this.zh = zh;
        this.en = en;
    }
}
