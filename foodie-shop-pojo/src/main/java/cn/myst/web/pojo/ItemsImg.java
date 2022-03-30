package cn.myst.web.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */

/**
 * 商品图片
 */
@Schema(title = "cn-myst-web-pojo-ItemsImg")
@Data
@TableName(value = "items_img")
public class ItemsImg implements Serializable {
    /**
     * 图片主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @Schema(name = "图片主键")
    private String id;

    /**
     * 商品外键id 商品外键id
     */
    @TableField(value = "item_id")
    @Schema(name = "商品外键id 商品外键id")
    private String itemId;

    /**
     * 图片地址 图片地址
     */
    @TableField(value = "url")
    @Schema(name = "图片地址 图片地址")
    private String url;

    /**
     * 顺序 图片顺序，从小到大
     */
    @TableField(value = "sort")
    @Schema(name = "顺序 图片顺序，从小到大")
    private Integer sort;

    /**
     * 是否主图 是否主图，1：是，0：否
     */
    @TableField(value = "is_main")
    @Schema(name = "是否主图 是否主图，1：是，0：否")
    private Integer isMain;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @Schema(name = "创建时间")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @Schema(name = "更新时间")
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