package cn.myst.web.tcc.demo.domain.db131;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Data
@TableName(value = "t_user")
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "sex")
    private Integer sex;

    @TableField(value = "age")
    private Integer age;

    @TableField(value = "update_count")
    private Integer updateCount;

    @TableField(value = "version")
    private Integer version;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USERNAME = "username";

    public static final String COL_SEX = "sex";

    public static final String COL_AGE = "age";

    public static final String COL_UPDATE_COUNT = "update_count";

    public static final String COL_VERSION = "version";
}