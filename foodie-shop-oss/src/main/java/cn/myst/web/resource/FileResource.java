package cn.myst.web.resource;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ziming.xing
 * Create Date：2022/3/31
 */
@Getter
@ToString
@Component
// 配置属性前缀 注：多环境配置${spring.profiles.active}下无效，需使用@Value注解指定全路径
//@ConfigurationProperties(prefix = "file")
@PropertySource(value = "classpath:config/upload/file-" + "${spring.profiles.active}" + ".properties")
public class FileResource {

    @Value("${file.server.url}")
    String serverUrl;

    @Value("${file.endpoint}")
    String endpoint;

    @Value("${file.accessKeyId}")
    String accessKeyId;

    @Value("${file.accessKeySecret}")
    String accessKeySecret;

    @Value("${file.bucketName}")
    String bucketName;

    @Value("${file.objectName}")
    String objectName;

    @Value("${file.oss.server.url}")
    String OSSServerUrl;
}