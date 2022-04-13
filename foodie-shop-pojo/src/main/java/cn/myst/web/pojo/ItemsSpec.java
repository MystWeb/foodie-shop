package cn.myst.web.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */

/**
 * 商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计
 */
@Schema(title = "cn-myst-web-pojo-ItemsSpec")
@Data
@TableName(value = "items_spec")
public class ItemsSpec implements Serializable {
    /**
     * 商品规格id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @Schema(title = "商品规格id")
    private String id;

    /**
     * 商品外键id
     */
    @TableField(value = "item_id")
    @Schema(title = "商品外键id")
    private String itemId;

    /**
     * 规格名称
     */
    @TableField(value = "`name`")
    @Schema(title = "规格名称")
    private String name;

    /**
     * 库存
     */
    @TableField(value = "stock")
    @Schema(title = "库存")
    private Integer stock;

    /**
     * 折扣力度
     */
    @TableField(value = "discounts")
    @Schema(title = "折扣力度")
    private BigDecimal discounts;

    /**
     * 优惠价
     */
    @TableField(value = "price_discount")
    @Schema(title = "优惠价")
    private Integer priceDiscount;

    /**
     * 原价
     */
    @TableField(value = "price_normal")
    @Schema(title = "原价")
    private Integer priceNormal;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @Schema(title = "创建时间")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @Schema(title = "更新时间")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ITEM_ID = "item_id";

    public static final String COL_NAME = "name";

    public static final String COL_STOCK = "stock";

    public static final String COL_DISCOUNTS = "discounts";

    public static final String COL_PRICE_DISCOUNT = "price_discount";

    public static final String COL_PRICE_NORMAL = "price_normal";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}