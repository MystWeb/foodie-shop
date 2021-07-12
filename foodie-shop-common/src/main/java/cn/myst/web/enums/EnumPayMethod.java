package cn.myst.web.enums;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 * 支付方式 枚举
 */
public enum EnumPayMethod {
    WE_CHAT(1, "微信"),
    ALI_PAY(2, "支付宝"),

    ;

    public final Integer type;
    public final String value;

    EnumPayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
