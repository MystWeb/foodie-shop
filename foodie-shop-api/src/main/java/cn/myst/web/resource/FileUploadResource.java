package cn.myst.web.resource;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ziming.xing
 * Create Date：2021/7/19
 */
@Getter
@ToString
@Component
// 配置属性前缀 注：多环境配置${spring.profiles.active}下无效，需使用@Value注解指定全路径
//@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:config/upload/file-upload-" + "${spring.profiles.active}" + ".properties")
public class FileUploadResource {

    @Value("${file.image.user.face.location}")
    String imageUserFaceLocation;

    @Value("${file.image.server.url}")
    String imageServerUrl;
}
