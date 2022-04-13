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
 * 商品分类
 */
@Schema(title = "cn-myst-web-pojo-Category")
@Data
@TableName(value = "category")
public class Category implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(title = "主键")
    private Integer id;

    /**
     * 分类名称
     */
    @TableField(value = "`name`")
    @Schema(title = "分类名称")
    private String name;

    /**
     * 分类类型
     */
    @TableField(value = "`type`")
    @Schema(title = "分类类型")
    private Integer type;

    /**
     * 父id
     */
    @TableField(value = "father_id")
    @Schema(title = "父id")
    private Integer fatherId;

    /**
     * 图标
     */
    @TableField(value = "logo")
    @Schema(title = "图标")
    private String logo;

    /**
     * 口号
     */
    @TableField(value = "slogan")
    @Schema(title = "口号")
    private String slogan;

    /**
     * 分类图
     */
    @TableField(value = "cat_image")
    @Schema(title = "分类图")
    private String catImage;

    /**
     * 背景颜色
     */
    @TableField(value = "bg_color")
    @Schema(title = "背景颜色")
    private String bgColor;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_TYPE = "type";

    public static final String COL_FATHER_ID = "father_id";

    public static final String COL_LOGO = "logo";

    public static final String COL_SLOGAN = "slogan";

    public static final String COL_CAT_IMAGE = "cat_image";

    public static final String COL_BG_COLOR = "bg_color";
}