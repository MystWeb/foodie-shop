package cn.myst.web.sharding.jdbc.demo.domain;

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
@TableName(value = "area")
public class Area implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    @TableField(value = "`name`")
    private String name;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";
}