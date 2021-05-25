package cn.myst.web.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
/**
    * 订单状态表;订单的每个状态更改都需要进行记录
10：待付款  20：已付款，待发货  30：已发货，待收货（7天自动确认）  40：交易成功（此时可以评价）50：交易关闭（待付款时，用户取消 或 长时间未付款，系统识别后自动关闭）
退货/退货，此分支流程不做，所以不加入
    */
@ApiModel(value="cn-myst-web-pojo-OrderStatus")
@Data
@TableName(value = "order_status")
public class OrderStatus implements Serializable {
    /**
     * 订单ID;对应订单表的主键id
     */
    @TableId(value = "order_id", type = IdType.INPUT)
    @ApiModelProperty(value="订单ID;对应订单表的主键id")
    private String orderId;

    /**
     * 订单状态
     */
    @TableField(value = "order_status")
    @ApiModelProperty(value="订单状态")
    private Integer orderStatus;

    /**
     * 订单创建时间;对应[10:待付款]状态
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="订单创建时间;对应[10:待付款]状态")
    private Date createdTime;

    /**
     * 支付成功时间;对应[20:已付款，待发货]状态
     */
    @TableField(value = "pay_time")
    @ApiModelProperty(value="支付成功时间;对应[20:已付款，待发货]状态")
    private Date payTime;

    /**
     * 发货时间;对应[30：已发货，待收货]状态
     */
    @TableField(value = "deliver_time")
    @ApiModelProperty(value="发货时间;对应[30：已发货，待收货]状态")
    private Date deliverTime;

    /**
     * 交易成功时间;对应[40：交易成功]状态
     */
    @TableField(value = "success_time")
    @ApiModelProperty(value="交易成功时间;对应[40：交易成功]状态")
    private Date successTime;

    /**
     * 交易关闭时间;对应[50：交易关闭]状态
     */
    @TableField(value = "close_time")
    @ApiModelProperty(value="交易关闭时间;对应[50：交易关闭]状态")
    private Date closeTime;

    /**
     * 留言时间;用户在交易成功后的留言时间
     */
    @TableField(value = "comment_time")
    @ApiModelProperty(value="留言时间;用户在交易成功后的留言时间")
    private Date commentTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_ORDER_STATUS = "order_status";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_PAY_TIME = "pay_time";

    public static final String COL_DELIVER_TIME = "deliver_time";

    public static final String COL_SUCCESS_TIME = "success_time";

    public static final String COL_CLOSE_TIME = "close_time";

    public static final String COL_COMMENT_TIME = "comment_time";
}