package cn.myst.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ziming.xing
 * Create Date：2021/6/29
 * 地址校验 枚举
 */
@AllArgsConstructor
@Getter
public enum EnumAddressValidation {
    CONSIGNEE_CANNOT_BE_EMPTY("收货人不能为空", "CONSIGNEE_CANNOT_BE_EMPTY"),
    RECEIVER_NAME_IS_NOT_TOO_LONG("收货人姓名不能太长", "RECEIVER_NAME_IS_NOT_TOO_LONG"),
    CONSIGNEE_MOBILE_PHONE_NUMBER_CANNOT_BE_EMPTY("收货人手机号不能为空", "CONSIGNEE_MOBILE_PHONE_NUMBER_CANNOT_BE_EMPTY"),
    CONSIGNEE_MOBILE_PHONE_NUMBER_LENGTH_IS_INCORRECT("收货人手机号长度不正确", "CONSIGNEE_MOBILE_PHONE_NUMBER_LENGTH_IS_INCORRECT"),
    CONSIGNEE_MOBILE_PHONE_NUMBER_IS_INCORRECT("收货人手机号格式不正确", "CONSIGNEE_MOBILE_PHONE_NUMBER_IS_INCORRECT"),
    RECEIPT_ADDRESS_INFORMATION_CANNOT_BE_EMPTY("收货地址信息不能为空", "RECEIPT_ADDRESS_INFORMATION_CANNOT_BE_EMPTY"),
    ADDRESS_ID_CANNOT_BE_EMPTY("addressId不能为空", "ADDRESS_ID_CANNOT_BE_EMPTY"),
    USER_ID_CANNOT_BE_EMPTY("userId不能为空", "USER_ID_CANNOT_BE_EMPTY"),

    ;

    public final String zh;
    public final String en;

}
