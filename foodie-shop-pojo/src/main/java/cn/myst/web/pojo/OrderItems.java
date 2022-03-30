package cn.myst.web.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */

/**
 * 订单商品关联表
 */
@Schema(title = "cn-myst-web-pojo-OrderItems")
@Data
@TableName(value = "order_items")
public class OrderItems implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @Schema(name = "主键id")
    private String id;

    /**
     * 归属订单id
     */
    @TableField(value = "order_id")
    @Schema(name = "归属订单id")
    private String orderId;

    /**
     * 商品id
     */
    @TableField(value = "item_id")
    @Schema(name = "商品id")
    private String itemId;

    /**
     * 商品图片
     */
    @TableField(value = "item_img")
    @Schema(name = "商品图片")
    private String itemImg;

    /**
     * 商品名称
     */
    @TableField(value = "item_name")
    @Schema(name = "商品名称")
    private String itemName;

    /**
     * 规格id
     */
    @TableField(value = "item_spec_id")
    @Schema(name = "规格id")
    private String itemSpecId;

    /**
     * 规格名称
     */
    @TableField(value = "item_spec_name")
    @Schema(name = "规格名称")
    private String itemSpecName;

    /**
     * 成交价格
     */
    @TableField(value = "price")
    @Schema(name = "成交价格")
    private Integer price;

    /**
     * 购买数量
     */
    @TableField(value = "buy_counts")
    @Schema(name = "购买数量")
    private Integer buyCounts;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_ITEM_ID = "item_id";

    public static final String COL_ITEM_IMG = "item_img";

    public static final String COL_ITEM_NAME = "item_name";

    public static final String COL_ITEM_SPEC_ID = "item_spec_id";

    public static final String COL_ITEM_SPEC_NAME = "item_spec_name";

    public static final String COL_PRICE = "price";

    public static final String COL_BUY_COUNTS = "buy_counts";
}