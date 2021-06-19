package cn.myst.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ziming.xing
 * Create Date：2021/6/20
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SortColumn {
    /**
     * 指定排序列名
     *
     * @return 列名
     */
    String value() default "";

    /**
     * 表别名
     *
     * @return 列名
     */
    String tableAlias() default "";
}
