package cn.myst.web.tcc.demo.domain.db132;

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
 * Create Date：2022/5/24
 */
@Data
@TableName(value = "t_order")
public class Order implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 0：未支付；1：已支付；
     */
    @TableField(value = "order_status")
    private Integer orderStatus;

    /**
     * 订单金额
     */
    @TableField(value = "order_amount")
    private BigDecimal orderAmount;

    /**
     * 收货人
     */
    @TableField(value = "receive_user")
    private String receiveUser;

    /**
     * 收货人电话
     */
    @TableField(value = "receive_mobile")
    private String receiveMobile;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "create_user")
    private Integer createUser;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "update_user")
    private Integer updateUser;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ORDER_STATUS = "order_status";

    public static final String COL_ORDER_AMOUNT = "order_amount";

    public static final String COL_RECEIVE_USER = "receive_user";

    public static final String COL_RECEIVE_MOBILE = "receive_mobile";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_USER = "create_user";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_UPDATE_USER = "update_user";
}