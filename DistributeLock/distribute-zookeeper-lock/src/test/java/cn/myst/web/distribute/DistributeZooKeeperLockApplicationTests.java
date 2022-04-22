package cn.myst.web.distribute;

import cn.myst.web.distribute.lock.ZooKeeperLock;
import cn.myst.web.distribute.resource.ZooKeeperResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class DistributeZooKeeperLockApplicationTests {

    @Resource
    private ZooKeeperResource zooKeeperResource;

    @Test
    void contextLoads() {
    }

    @Test
    public void testZooKeeperLock() throws Exception {
        ZooKeeperLock zkLock = new ZooKeeperLock(zooKeeperResource.getZookeeperAddress(), zooKeeperResource.getZookeeperSessionTimeout());
        boolean lock = zkLock.getLock("order");
        log.info("获得锁的结果：" + lock);

        zkLock.close();
    }

    @Test
    public void testCuratorLock() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zooKeeperResource.getZookeeperAddress(), retryPolicy);
        client.start();
        InterProcessMutex lock = new InterProcessMutex(client, "/order");
        try {
            if (lock.acquire(30, TimeUnit.SECONDS)) {
                try {
                    log.info("我获得了锁！！！");
                } finally {
                    lock.release();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }

}
