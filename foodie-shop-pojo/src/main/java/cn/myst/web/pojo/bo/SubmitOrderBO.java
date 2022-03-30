package cn.myst.web.pojo.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/6/30
 */
@Schema(title = "创建订单BO", description = "客户端，用户传入的数据封装在此entity中")
@Data
public class SubmitOrderBO implements Serializable {

    @Schema(name = "用户id", required = true)
    private String userId;

    @Schema(name = "规格ids", required = true)
    private String itemSpecIds;

    @Schema(name = "地址id", required = true)
    private String addressId;

    @Schema(name = "支付方式", required = true)
    private Integer payMethod;

    @Schema(name = "买家留言")
    private String leftMsg;

    private static final long serialVersionUID = 987105845404795161L;
}
