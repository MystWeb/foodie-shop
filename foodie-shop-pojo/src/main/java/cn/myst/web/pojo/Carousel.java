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
    * 轮播图 
    */
@ApiModel(value="cn-myst-web-pojo-Carousel")
@Data
@TableName(value = "carousel")
public class Carousel implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="主键")
    private String id;

    /**
     * 图片 图片地址
     */
    @TableField(value = "image_url")
    @ApiModelProperty(value="图片 图片地址")
    private String imageUrl;

    /**
     * 背景色
     */
    @TableField(value = "background_color")
    @ApiModelProperty(value="背景色")
    private String backgroundColor;

    /**
     * 商品id 商品id
     */
    @TableField(value = "item_id")
    @ApiModelProperty(value="商品id 商品id")
    private String itemId;

    /**
     * 商品分类id 商品分类id
     */
    @TableField(value = "cat_id")
    @ApiModelProperty(value="商品分类id 商品分类id")
    private String catId;

    /**
     * 轮播图类型 轮播图类型，用于判断，可以根据商品id或者分类进行页面跳转，1：商品 2：分类
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value="轮播图类型 轮播图类型，用于判断，可以根据商品id或者分类进行页面跳转，1：商品 2：分类")
    private Integer type;

    /**
     * 轮播图展示顺序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="轮播图展示顺序")
    private Integer sort;

    /**
     * 是否展示
     */
    @TableField(value = "is_show")
    @ApiModelProperty(value="是否展示")
    private Integer isShow;

    /**
     * 创建时间 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间 创建时间")
    private Date createTime;

    /**
     * 更新时间 更新
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间 更新")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_IMAGE_URL = "image_url";

    public static final String COL_BACKGROUND_COLOR = "background_color";

    public static final String COL_ITEM_ID = "item_id";

    public static final String COL_CAT_ID = "cat_id";

    public static final String COL_TYPE = "type";

    public static final String COL_SORT = "sort";

    public static final String COL_IS_SHOW = "is_show";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";
}