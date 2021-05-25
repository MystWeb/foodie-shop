package cn.myst.web.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */

/**
 * 用户表
 */
@ApiModel(value = "cn-myst-web-pojo-Users")
@Data
@TableName(value = "users")
public class Users implements Serializable {
    /**
     * 主键id 用户id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键id 用户id")
    private String id;

    /**
     * 用户名 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名 用户名")
    private String username;

    /**
     * 密码 密码
     */
    @JsonIgnore
    @TableField(value = "`password`")
    @ApiModelProperty(value = "密码 密码")
    private String password;

    /**
     * 昵称 昵称
     */
    @TableField(value = "nickname")
    @ApiModelProperty(value = "昵称 昵称")
    private String nickname;

    /**
     * 真实姓名
     */
    @JsonIgnore
    @TableField(value = "realname")
    @ApiModelProperty(value = "真实姓名")
    private String realname;

    /**
     * 头像
     */
    @TableField(value = "face")
    @ApiModelProperty(value = "头像")
    private String face;

    /**
     * 手机号 手机号
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value = "手机号 手机号")
    private String mobile;

    /**
     * 邮箱地址 邮箱地址
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱地址 邮箱地址")
    private String email;

    /**
     * 性别 性别 1:男  0:女  2:保密
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "性别 性别 1:男  0:女  2:保密")
    private Integer sex;

    /**
     * 生日 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "生日 生日")
    private Date birthday;

    /**
     * 创建时间 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间 创建时间")
    private Date createdTime;

    /**
     * 更新时间 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间 更新时间")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_NICKNAME = "nickname";

    public static final String COL_REALNAME = "realname";

    public static final String COL_FACE = "face";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_EMAIL = "email";

    public static final String COL_SEX = "sex";

    public static final String COL_BIRTHDAY = "birthday";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}