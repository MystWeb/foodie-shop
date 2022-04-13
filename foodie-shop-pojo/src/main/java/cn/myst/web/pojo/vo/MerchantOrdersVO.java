package cn.myst.web.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/7/4
 */
@Schema(title = "商户订单", name = "用户传给支付中心")
@Data
public class MerchantOrdersVO implements Serializable {

    @Schema(title = "商户订单号")
    private String merchantOrderId;

    @Schema(title = "商户方的发起用户的用户主键id")
    private String merchantUserId;

    @Schema(title = "实际支付总金额（包含商户所支付的订单费邮费总额）")
    private Integer amount;

    @Schema(title = "支付方式 1:微信   2:支付宝")
    private Integer payMethod;

    @Schema(title = "支付成功后的回调地址（学生自定义）")
    private String returnUrl;

    private static final long serialVersionUID = 7219618759986415299L;
}
