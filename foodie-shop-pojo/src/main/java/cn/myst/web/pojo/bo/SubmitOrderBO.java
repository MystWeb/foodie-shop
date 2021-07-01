package cn.myst.web.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 */
@ApiModel(value = "创建订单BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class SubmitOrderBO implements Serializable {

    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @ApiModelProperty(value = "规格ids", required = true)
    private String itemSpecIds;

    @ApiModelProperty(value = "地址id", required = true)
    private String addressId;

    @ApiModelProperty(value = "支付方式", required = true)
    private Integer payMethod;

    @ApiModelProperty(value = "买家留言")
    private String leftMsg;

    private static final long serialVersionUID = 987105845404795161L;
}
