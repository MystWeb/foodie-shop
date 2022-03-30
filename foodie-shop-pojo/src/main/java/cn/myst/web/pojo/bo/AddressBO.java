package cn.myst.web.pojo.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/29
 */
@Schema(title = "地址BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class AddressBO implements Serializable {
    @Schema(name = "地址id")
    private String addressId;

    @Schema(name = "关联用户id")
    private String userId;

    @Schema(name = "收件人姓名")
    private String receiver;

    @Schema(name = "收件人手机号")
    private String mobile;

    @Schema(name = "省份")
    private String province;

    @Schema(name = "城市")
    private String city;

    @Schema(name = "区县")
    private String district;

    @Schema(name = "详细地址")
    private String detail;

    private static final long serialVersionUID = -8632881206772473412L;
}
