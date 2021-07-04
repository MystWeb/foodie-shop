package cn.myst.web.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/7/4
 */
@ApiModel(value = "自定义订单VO")
@Data
public class OrderVO implements Serializable {

    @ApiModelProperty(value = "商户订单号")
    private String orderId;

    @ApiModelProperty(value = "商户订单")
    private MerchantOrdersVO merchantOrdersVO;

    private static final long serialVersionUID = -8583695550044976795L;
}
