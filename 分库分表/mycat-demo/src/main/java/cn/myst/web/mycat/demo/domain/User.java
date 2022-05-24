package cn.myst.web.mycat.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Data
@TableName(value = "`user`")
public class User {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 省ID
     */
    @TableField(value = "province_id")
    private Integer provinceId;

    public static final String COL_ID = "id";

    public static final String COL_USERNAME = "username";

    public static final String COL_PROVINCE_ID = "province_id";
}