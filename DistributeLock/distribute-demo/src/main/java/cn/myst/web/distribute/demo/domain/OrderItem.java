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
    * 订单商品表
    */
@Data
@TableName(value = "order_item")
public class OrderItem implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    @TableField(value = "order_id")
    private Integer orderId;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private Integer productId;

    /**
     * 购买价格
     */
    @TableField(value = "purchase_price")
    private BigDecimal purchasePrice;

    /**
     * 购买数量
     */
    @TableField(value = "purchase_num")
    private Integer purchaseNum;

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

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_PRODUCT_ID = "product_id";

    public static final String COL_PURCHASE_PRICE = "purchase_price";

    public static final String COL_PURCHASE_NUM = "purchase_num";

    public static final String COL_CREATE_USER = "create_user";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_USER = "update_user";

    public static final String COL_UPDATE_TIME = "update_time";
}