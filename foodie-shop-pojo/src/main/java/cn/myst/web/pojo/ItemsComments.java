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
 * 商品评价表
 */
@Schema(title = "cn-myst-web-pojo-ItemsComments")
@Data
@TableName(value = "items_comments")
public class ItemsComments implements Serializable {
    /**
     * id主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @Schema(name = "id主键")
    private String id;

    /**
     * 用户id 用户名须脱敏
     */
    @TableField(value = "user_id")
    @Schema(name = "用户id 用户名须脱敏")
    private String userId;

    /**
     * 商品id
     */
    @TableField(value = "item_id")
    @Schema(name = "商品id")
    private String itemId;

    /**
     * 商品名称
     */
    @TableField(value = "item_name")
    @Schema(name = "商品名称")
    private String itemName;

    /**
     * 商品规格id 可为空
     */
    @TableField(value = "item_spec_id")
    @Schema(name = "商品规格id 可为空")
    private String itemSpecId;

    /**
     * 规格名称 可为空
     */
    @TableField(value = "sepc_name")
    @Schema(name = "规格名称 可为空")
    private String sepcName;

    /**
     * 评价等级 1：好评 2：中评 3：差评
     */
    @TableField(value = "comment_level")
    @Schema(name = "评价等级 1：好评 2：中评 3：差评")
    private Integer commentLevel;

    /**
     * 评价内容
     */
    @TableField(value = "content")
    @Schema(name = "评价内容")
    private String content;

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

    public static final String COL_USER_ID = "user_id";

    public static final String COL_ITEM_ID = "item_id";

    public static final String COL_ITEM_NAME = "item_name";

    public static final String COL_ITEM_SPEC_ID = "item_spec_id";

    public static final String COL_SEPC_NAME = "sepc_name";

    public static final String COL_COMMENT_LEVEL = "comment_level";

    public static final String COL_CONTENT = "content";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}