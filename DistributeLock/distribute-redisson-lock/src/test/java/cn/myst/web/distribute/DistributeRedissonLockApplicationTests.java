package cn.myst.web.distribute;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class DistributeRedissonLockApplicationTests {

    @Test
    void contextLoads() {
    }

    void redissonLock() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.100.146:6379");
        RedissonClient redissonClient = Redisson.create(config);

        RLock rLock = redissonClient.getLock("order");

        rLock.lock(30, TimeUnit.SECONDS);
        log.info("我获得了锁");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            log.info("我释放了锁");
            rLock.unlock();
        }
    }

}
