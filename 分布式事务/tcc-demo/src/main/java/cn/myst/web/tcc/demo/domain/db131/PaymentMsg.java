package cn.myst.web.tcc.demo.domain.db131;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Data
@TableName(value = "payment_msg")
public class PaymentMsg implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "order_id")
    private Integer orderId;

    /**
     * 0：未发送；1：发送成功；2：超过最大发送次数；
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 失败次数：最大5次
     */
    @TableField(value = "failure_count")
    private Integer failureCount;

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

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_STATUS = "status";

    public static final String COL_FAILURE_COUNT = "failure_count";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_CREATE_USER = "create_user";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_UPDATE_USER = "update_user";
}