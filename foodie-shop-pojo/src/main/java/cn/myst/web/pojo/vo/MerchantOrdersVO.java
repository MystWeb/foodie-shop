package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/7/4
 */
@ApiModel(value = "商户订单", description = "用户传给支付中心")
@Data
public class MerchantOrdersVO implements Serializable {

    @ApiModelProperty(value = "商户订单号")
    private String merchantOrderId;

    @ApiModelProperty(value = "商户方的发起用户的用户主键id")
    private String merchantUserId;

    @ApiModelProperty(value = "实际支付总金额（包含商户所支付的订单费邮费总额）")
    private Integer amount;

    @ApiModelProperty(value = "支付方式 1:微信   2:支付宝")
    private Integer payMethod;

    @ApiModelProperty(value = "支付成功后的回调地址（学生自定义）")
    private String returnUrl;

    private static final long serialVersionUID = 7219618759986415299L;
}
