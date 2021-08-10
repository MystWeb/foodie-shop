package cn.myst.web.config;

import cn.myst.web.redisson.impl.RedisDistributedLocker;
import cn.myst.web.utils.RedisLockUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * @author ziming.xing
 * Create Time：2020/5/25
 */

@Data
@EnableConfigurationProperties({RedisProperties.class})
@Import({RedisDistributedLocker.class, RedisLockUtils.class})
public class RedissonConfig {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        if (redisProperties.getCluster() != null) {
            // 集群模式配置
            config.useClusterServers()
                    .addNodeAddress(redisProperties.getCluster().getNodes().toArray(new String[0]));
        } else if (redisProperties.getSentinel() != null) {
            //添加哨兵配置
            config.useMasterSlaveServers().setMasterAddress(redisProperties.getSentinel().getMaster())
                    .addSlaveAddress(redisProperties.getSentinel().getNodes().toArray(new String[0]));
        } else {
            //单节点
            config.useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        }
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            config.useClusterServers().setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
