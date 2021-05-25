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
    * 订单表;
    */
@ApiModel(value="cn-myst-web-pojo-Orders")
@Data
@TableName(value = "orders")
public class Orders implements Serializable {
    /**
     * 订单主键;同时也是订单编号
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value="订单主键;同时也是订单编号")
    private String id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id")
    private String userId;

    /**
     * 收货人快照
     */
    @TableField(value = "receiver_name")
    @ApiModelProperty(value="收货人快照")
    private String receiverName;

    /**
     * 收货人手机号快照
     */
    @TableField(value = "receiver_mobile")
    @ApiModelProperty(value="收货人手机号快照")
    private String receiverMobile;

    /**
     * 收货地址快照
     */
    @TableField(value = "receiver_address")
    @ApiModelProperty(value="收货地址快照")
    private String receiverAddress;

    /**
     * 订单总价格
     */
    @TableField(value = "total_amount")
    @ApiModelProperty(value="订单总价格")
    private Integer totalAmount;

    /**
     * 实际支付总价格
     */
    @TableField(value = "real_pay_amount")
    @ApiModelProperty(value="实际支付总价格")
    private Integer realPayAmount;

    /**
     * 邮费;默认可以为零，代表包邮
     */
    @TableField(value = "post_amount")
    @ApiModelProperty(value="邮费;默认可以为零，代表包邮")
    private Integer postAmount;

    /**
     * 支付方式
     */
    @TableField(value = "pay_method")
    @ApiModelProperty(value="支付方式")
    private Integer payMethod;

    /**
     * 买家留言
     */
    @TableField(value = "left_msg")
    @ApiModelProperty(value="买家留言")
    private String leftMsg;

    /**
     * 扩展字段
     */
    @TableField(value = "extand")
    @ApiModelProperty(value="扩展字段")
    private String extand;

    /**
     * 买家是否评价;1：已评价，0：未评价
     */
    @TableField(value = "is_comment")
    @ApiModelProperty(value="买家是否评价;1：已评价，0：未评价")
    private Integer isComment;

    /**
     * 逻辑删除状态;1: 删除 0:未删除
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value="逻辑删除状态;1: 删除 0:未删除")
    private Integer isDelete;

    /**
     * 创建时间（成交时间）
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value="创建时间（成交时间）")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value="更新时间")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_RECEIVER_NAME = "receiver_name";

    public static final String COL_RECEIVER_MOBILE = "receiver_mobile";

    public static final String COL_RECEIVER_ADDRESS = "receiver_address";

    public static final String COL_TOTAL_AMOUNT = "total_amount";

    public static final String COL_REAL_PAY_AMOUNT = "real_pay_amount";

    public static final String COL_POST_AMOUNT = "post_amount";

    public static final String COL_PAY_METHOD = "pay_method";

    public static final String COL_LEFT_MSG = "left_msg";

    public static final String COL_EXTAND = "extand";

    public static final String COL_IS_COMMENT = "is_comment";

    public static final String COL_IS_DELETE = "is_delete";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}