package cn.myst.web.rabbitmq.task.annotation;

import cn.myst.web.rabbitmq.task.autoconfigure.JobParserAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ziming.xing
 * Create Date：2022/4/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(JobParserAutoConfiguration.class)
public @interface EnableElasticJob {
}
