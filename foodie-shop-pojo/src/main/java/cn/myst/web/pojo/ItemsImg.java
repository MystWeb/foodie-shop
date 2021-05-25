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
    * 商品图片 
    */
@ApiModel(value="cn-myst-web-pojo-ItemsImg")
@Data
@TableName(value = "items_img")
public class ItemsImg implements Serializable {
    /**
     * 图片主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="图片主键")
    private String id;

    /**
     * 商品外键id 商品外键id
     */
    @TableField(value = "item_id")
    @ApiModelProperty(value="商品外键id 商品外键id")
    private String itemId;

    /**
     * 图片地址 图片地址
     */
    @TableField(value = "url")
    @ApiModelProperty(value="图片地址 图片地址")
    private String url;

    /**
     * 顺序 图片顺序，从小到大
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="顺序 图片顺序，从小到大")
    private Integer sort;

    /**
     * 是否主图 是否主图，1：是，0：否
     */
    @TableField(value = "is_main")
    @ApiModelProperty(value="是否主图 是否主图，1：是，0：否")
    private Integer isMain;

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

    public static final String COL_URL = "url";

    public static final String COL_SORT = "sort";

    public static final String COL_IS_MAIN = "is_main";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}