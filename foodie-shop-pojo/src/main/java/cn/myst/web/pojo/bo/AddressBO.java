package cn.myst.web.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/29
 */
@ApiModel(value = "地址BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class AddressBO implements Serializable {
    @ApiModelProperty(value = "地址id")
    private String addressId;

    @ApiModelProperty(value = "关联用户id")
    private String userId;

    @ApiModelProperty(value = "收件人姓名")
    private String receiver;

    @ApiModelProperty(value = "收件人手机号")
    private String mobile;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区县")
    private String district;

    @ApiModelProperty(value = "详细地址")
    private String detail;

    private static final long serialVersionUID = -8632881206772473412L;
}
