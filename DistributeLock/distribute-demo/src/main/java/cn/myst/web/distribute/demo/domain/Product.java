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
    * 商品表
    */
@Data
@TableName(value = "product")
public class Product implements Serializable {
    /**
     * 商品id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 商品金额
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 商品库存数量
     */
    @TableField(value = "`count`")
    private Integer count;

    /**
     * 商品描述
     */
    @TableField(value = "product_desc")
    private String productDesc;

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

    public static final String COL_PRODUCT_NAME = "product_name";

    public static final String COL_PRICE = "price";

    public static final String COL_COUNT = "count";

    public static final String COL_PRODUCT_DESC = "product_desc";

    public static final String COL_CREATE_USER = "create_user";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_USER = "update_user";

    public static final String COL_UPDATE_TIME = "update_time";
}