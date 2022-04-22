package cn.myst.web.distribute.resource;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ziming.xing
 * Create Date：2022/4/22
 */
@Getter
@ToString
@Component
// 配置属性前缀 注：多环境配置${spring.profiles.active}下无效，需使用@Value注解指定全路径
//@ConfigurationProperties(prefix = "file")
//@PropertySource("classpath:config/zookeeper-" + "${spring.profiles.active}" + ".properties")
@PropertySource("classpath:config/zookeeper-dev.properties")
public class ZooKeeperResource {

    @Value("${zookeeper.address}")
    private String zookeeperAddress;

    @Value("${zookeeper.session.timeout}")
    private Integer zookeeperSessionTimeout;
}
