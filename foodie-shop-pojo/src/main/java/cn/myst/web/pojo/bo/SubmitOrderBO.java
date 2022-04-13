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

    @Schema(title = "用户id", required = true)
    private String userId;

    @Schema(title = "规格ids", required = true)
    private String itemSpecIds;

    @Schema(title = "地址id", required = true)
    private String addressId;

    @Schema(title = "支付方式", required = true)
    private Integer payMethod;

    @Schema(title = "买家留言")
    private String leftMsg;

    private static final long serialVersionUID = 987105845404795161L;
}
