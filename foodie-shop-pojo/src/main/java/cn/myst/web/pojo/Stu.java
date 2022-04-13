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
@Schema(title = "cn-myst-web-pojo-Stu")
@Data
@TableName(value = "stu")
public class Stu implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(title = "id")
    private Integer id;

    @TableField(value = "`name`")
    @Schema(title = "name")
    private String name;

    @TableField(value = "age")
    @Schema(title = "age")
    private Integer age;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_AGE = "age";
}