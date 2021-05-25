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
    * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表
    */
@ApiModel(value="cn-myst-web-pojo-Items")
@Data
@TableName(value = "items")
public class Items implements Serializable {
    /**
     * 商品主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="商品主键id")
    private String id;

    /**
     * 商品名称 商品名称
     */
    @TableField(value = "item_name")
    @ApiModelProperty(value="商品名称 商品名称")
    private String itemName;

    /**
     * 分类外键id 分类id
     */
    @TableField(value = "cat_id")
    @ApiModelProperty(value="分类外键id 分类id")
    private Integer catId;

    /**
     * 一级分类外键id
     */
    @TableField(value = "root_cat_id")
    @ApiModelProperty(value="一级分类外键id")
    private Integer rootCatId;

    /**
     * 累计销售 累计销售
     */
    @TableField(value = "sell_counts")
    @ApiModelProperty(value="累计销售 累计销售")
    private Integer sellCounts;

    /**
     * 上下架状态 上下架状态,1:上架 2:下架
     */
    @TableField(value = "on_off_status")
    @ApiModelProperty(value="上下架状态 上下架状态,1:上架 2:下架")
    private Integer onOffStatus;

    /**
     * 商品内容 商品内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value="商品内容 商品内容")
    private String content;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ITEM_NAME = "item_name";

    public static final String COL_CAT_ID = "cat_id";

    public static final String COL_ROOT_CAT_ID = "root_cat_id";

    public static final String COL_SELL_COUNTS = "sell_counts";

    public static final String COL_ON_OFF_STATUS = "on_off_status";

    public static final String COL_CONTENT = "content";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}