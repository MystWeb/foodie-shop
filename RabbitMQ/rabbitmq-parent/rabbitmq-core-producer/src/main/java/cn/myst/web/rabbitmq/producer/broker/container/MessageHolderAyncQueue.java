package cn.myst.web.rabbitmq.producer.broker.container;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.concurrent.*;

/**
 * MQ池化方案—异步线程池
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
@Slf4j
public class MessageHolderAyncQueue {

    public static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public static final int QUEUE_SIZE = 10000;

    private static final ExecutorService senderAsync = new ThreadPoolExecutor(THREAD_POOL_SIZE,
            THREAD_POOL_SIZE,
            60L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(QUEUE_SIZE),
            new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable runnable) {
                    Thread thread = new Thread(runnable);
                    thread.setName("rabbitmq_client_async_sender");
                    return thread;
                }
            },
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
                    log.error("async sender is error rejected, runnable: {}, executor: {}", runnable, executor);
                }
            });

    public static void submit(Runnable r) {
        senderAsync.submit(r);
    }
}
