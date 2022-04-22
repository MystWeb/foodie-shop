package cn.myst.web.distribute.demo;

import cn.myst.web.distribute.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest
class DistributeDemoApplicationTests {

    private final OrderService orderService;

    @Test
    public void concurrentOrder() throws InterruptedException {
//        Thread.sleep(60000);
        CountDownLatch cdl = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                try {
                    cyclicBarrier.await();
                    /* 超卖现象 */
//                    Integer orderId = orderService.createOrderForJavaCalculation();
//                    Integer orderId = orderService.createOrderForSQLCalculation();
//                    Integer orderId = orderService.createOrderBySynchronized();
                    /* 超卖解决 */
//                    Integer orderId = orderService.createOrderByPlatformTransactionManager();
//                    Integer orderId = orderService.createOrderBySynchronizedBlock();
//                    Integer orderId = orderService.createOrderByReentrantLock();
                    Integer orderId = orderService.createOrder();
                    log.info("订单id：" + orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();
                }
            });
        }
        cdl.await();
        es.shutdown();
    }

}
