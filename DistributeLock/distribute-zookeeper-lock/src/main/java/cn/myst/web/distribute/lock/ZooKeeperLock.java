package cn.myst.web.distribute.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/4/22
 */
@Slf4j
public class ZooKeeperLock implements AutoCloseable, Watcher {

    @Resource
    private ZooKeeper zooKeeper;
    private String zkNode;

    public ZooKeeperLock(String zookeeperAddress, Integer zookeeperSessionTimeout) throws IOException {
        this.zooKeeper = new ZooKeeper(zookeeperAddress, zookeeperSessionTimeout, this);
    }

    public boolean getLock(String businessCode) {
        try {
            //创建业务 根节点
            Stat stat = zooKeeper.exists("/" + businessCode, false);
            if (stat == null) {
                zooKeeper.create("/" + businessCode, businessCode.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);
            }

            //创建瞬时有序节点  /order/order_00000001
            zkNode = zooKeeper.create("/" + businessCode + "/" + businessCode + "_", businessCode.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);

            //获取业务节点下 所有的子节点
            List<String> childrenNodes = zooKeeper.getChildren("/" + businessCode, false);
            //子节点排序
            Collections.sort(childrenNodes);
            //获取序号最小的（第一个）子节点
            String firstNode = childrenNodes.get(0);
            //如果创建的节点是第一个子节点，则获得锁
            if (zkNode.endsWith(firstNode)) {
                return true;
            }
            //不是第一个子节点，则监听前一个节点
            String lastNode = firstNode;
            for (String node : childrenNodes) {
                if (zkNode.endsWith(node)) {
                    zooKeeper.exists("/" + businessCode + "/" + lastNode, true);
                    break;
                } else {
                    lastNode = node;
                }
            }
            synchronized (this) {
                wait();
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        zooKeeper.delete(zkNode, -1);
        zooKeeper.close();
        log.info("我已经释放了锁！");
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
            synchronized (this) {
                notify();
            }
        }
    }
}
