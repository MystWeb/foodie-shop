package cn.myst.web.distribute.config;

import cn.myst.web.distribute.resource.ZooKeeperResource;
import lombok.RequiredArgsConstructor;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ziming.xing
 * Create Date：2022/4/22
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CuratorConfig {

    private final ZooKeeperResource zooKeeperResource;

    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework getCuratorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.newClient(zooKeeperResource.getZookeeperAddress(), retryPolicy);
    }
}
