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
 * 用户地址表
 */
@Schema(title = "cn-myst-web-pojo-UserAddress")
@Data
@TableName(value = "user_address")
public class UserAddress implements Serializable {
    /**
     * 地址主键id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @Schema(title = "地址主键id")
    private String id;

    /**
     * 关联用户id
     */
    @TableField(value = "user_id")
    @Schema(title = "关联用户id")
    private String userId;

    /**
     * 收件人姓名
     */
    @TableField(value = "receiver")
    @Schema(title = "收件人姓名")
    private String receiver;

    /**
     * 收件人手机号
     */
    @TableField(value = "mobile")
    @Schema(title = "收件人手机号")
    private String mobile;

    /**
     * 省份
     */
    @TableField(value = "province")
    @Schema(title = "省份")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    @Schema(title = "城市")
    private String city;

    /**
     * 区县
     */
    @TableField(value = "district")
    @Schema(title = "区县")
    private String district;

    /**
     * 详细地址
     */
    @TableField(value = "detail")
    @Schema(title = "详细地址")
    private String detail;

    /**
     * 扩展字段
     */
    @TableField(value = "extand")
    @Schema(title = "扩展字段")
    private String extand;

    /**
     * 是否默认地址
     */
    @TableField(value = "is_default")
    @Schema(title = "是否默认地址")
    private Integer isDefault;

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

    public static final String COL_USER_ID = "user_id";

    public static final String COL_RECEIVER = "receiver";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_PROVINCE = "province";

    public static final String COL_CITY = "city";

    public static final String COL_DISTRICT = "district";

    public static final String COL_DETAIL = "detail";

    public static final String COL_EXTAND = "extand";

    public static final String COL_IS_DEFAULT = "is_default";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}