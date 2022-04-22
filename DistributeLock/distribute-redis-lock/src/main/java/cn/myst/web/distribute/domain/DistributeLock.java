package cn.myst.web.distribute.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @author ziming.xing
 * Create Date：2022/4/21
 */

/**
 * 分布式锁
 */
@Data
@TableName(value = "distribute_lock")
public class DistributeLock implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 业务代码（区分不同业务使用不同锁）
     */
    @TableField(value = "business_code")
    private String businessCode;

    /**
     * 业务名称
     */
    @TableField(value = "business_name")
    private String businessName;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_BUSINESS_CODE = "business_code";

    public static final String COL_BUSINESS_NAME = "business_name";
}