package cn.myst.web.distribute.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @author ziming.xing
 * Create Date：2022/4/20
 */
/**
    * 订单表
    */
@Data
@TableName(value = "`order`")
public class Order implements Serializable {
    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单状态：1=待支付
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

    /**
     * 收款人姓名
     */
    @TableField(value = "receiver_name")
    private String receiverName;

    /**
     * 收款人手机号
     */
    @TableField(value = "receiver_mobile")
    private String receiverMobile;

    /**
     * 订单金额
     */
    @TableField(value = "order_amount")
    private BigDecimal orderAmount;

    /**
     * 创建人
     */
    @TableField(value = "create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ORDER_STATUS = "order_status";

    public static final String COL_RECEIVER_NAME = "receiver_name";

    public static final String COL_RECEIVER_MOBILE = "receiver_mobile";

    public static final String COL_ORDER_AMOUNT = "order_amount";

    public static final String COL_CREATE_USER = "create_user";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_USER = "update_user";

    public static final String COL_UPDATE_TIME = "update_time";
}