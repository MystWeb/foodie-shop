package cn.myst.web.sharding.jdbc.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Data
@TableName(value = "order_item_1")
public class OrderItem implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    @TableField(value = "order_id")
    private Integer orderId;

    @TableField(value = "product_name")
    private String productName;

    @TableField(value = "num")
    private Integer num;

    @TableField(value = "user_id")
    private Integer userId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_PRODUCT_NAME = "product_name";

    public static final String COL_NUM = "num";

    public static final String COL_USER_ID = "user_id";
}