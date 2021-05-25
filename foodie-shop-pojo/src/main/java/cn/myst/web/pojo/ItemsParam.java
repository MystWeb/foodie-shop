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
    * 商品参数 
    */
@ApiModel(value="cn-myst-web-pojo-ItemsParam")
@Data
@TableName(value = "items_param")
public class ItemsParam implements Serializable {
    /**
     * 商品参数id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="商品参数id")
    private String id;

    /**
     * 商品外键id
     */
    @TableField(value = "item_id")
    @ApiModelProperty(value="商品外键id")
    private String itemId;

    /**
     * 产地 产地，例：中国江苏
     */
    @TableField(value = "produc_place")
    @ApiModelProperty(value="产地 产地，例：中国江苏")
    private String producPlace;

    /**
     * 保质期 保质期，例：180天
     */
    @TableField(value = "foot_period")
    @ApiModelProperty(value="保质期 保质期，例：180天")
    private String footPeriod;

    /**
     * 品牌名 品牌名，例：三只大灰狼
     */
    @TableField(value = "brand")
    @ApiModelProperty(value="品牌名 品牌名，例：三只大灰狼")
    private String brand;

    /**
     * 生产厂名 生产厂名，例：大灰狼工厂
     */
    @TableField(value = "factory_name")
    @ApiModelProperty(value="生产厂名 生产厂名，例：大灰狼工厂")
    private String factoryName;

    /**
     * 生产厂址 生产厂址，例：大灰狼生产基地
     */
    @TableField(value = "factory_address")
    @ApiModelProperty(value="生产厂址 生产厂址，例：大灰狼生产基地")
    private String factoryAddress;

    /**
     * 包装方式 包装方式，例：袋装
     */
    @TableField(value = "packaging_method")
    @ApiModelProperty(value="包装方式 包装方式，例：袋装")
    private String packagingMethod;

    /**
     * 规格重量 规格重量，例：35g
     */
    @TableField(value = "weight")
    @ApiModelProperty(value="规格重量 规格重量，例：35g")
    private String weight;

    /**
     * 存储方法 存储方法，例：常温5~25°
     */
    @TableField(value = "storage_method")
    @ApiModelProperty(value="存储方法 存储方法，例：常温5~25°")
    private String storageMethod;

    /**
     * 食用方式 食用方式，例：开袋即食
     */
    @TableField(value = "eat_method")
    @ApiModelProperty(value="食用方式 食用方式，例：开袋即食")
    private String eatMethod;

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

    public static final String COL_ITEM_ID = "item_id";

    public static final String COL_PRODUC_PLACE = "produc_place";

    public static final String COL_FOOT_PERIOD = "foot_period";

    public static final String COL_BRAND = "brand";

    public static final String COL_FACTORY_NAME = "factory_name";

    public static final String COL_FACTORY_ADDRESS = "factory_address";

    public static final String COL_PACKAGING_METHOD = "packaging_method";

    public static final String COL_WEIGHT = "weight";

    public static final String COL_STORAGE_METHOD = "storage_method";

    public static final String COL_EAT_METHOD = "eat_method";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}