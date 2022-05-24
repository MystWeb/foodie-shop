package cn.myst.web.sharding.jdbc.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Data
@TableName(value = "order_1")
public class Order implements Serializable {
    public static final String COL_ID = "id";
    @TableId(value = "order_id", type = IdType.INPUT)
    private Long orderId;

    @TableField(value = "order_amount")
    private BigDecimal orderAmount;

    @TableField(value = "order_status")
    private Integer orderStatus;

    @TableField(value = "user_id")
    private Integer userId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_ORDER_AMOUNT = "order_amount";

    public static final String COL_ORDER_STATUS = "order_status";

    public static final String COL_USER_ID = "user_id";
}