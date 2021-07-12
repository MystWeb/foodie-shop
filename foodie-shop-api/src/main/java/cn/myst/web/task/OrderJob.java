package cn.myst.web.task;

import cn.myst.web.service.OrderService;
import cn.myst.web.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author ziming.xing
 * Create Date：2021/7/12
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OrderJob {
    private final OrderService orderService;

    // 在线Cron表达式生成器：https://cron.qqe2.com/

    /**
     * 使用定时任务关闭超期未支付订单，会存在的弊端：
     * 1. 会有时间差，程序不严谨（10:39下单，11:00检查不足1小时，12:00检查，超过1小时多余39分钟）
     * 2. 不支持集群（单机没问题，使用集群后，就会有多个定时任务）解决方案：只使用一台计算机节点，单独用来运行所有的定时任务
     * 3. 会对数据库全表搜索，及其影响数据库性能：select * from order where orderStatus = 10
     * 定时任务，仅仅只适用于小型轻量级项目，传统项目
     * <p>
     * 后续会涉及到消息队列：MQ-> RabbitMQ, RocketMQ, Kafka, ZeroMQ...
     * 延时任务（队列）
     * 10:12分下单的，未付款（10状态），11:12分检查，如果当时状态还是10，则直接关闭订单即可
     */
//    @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder() {
        Class<? extends OrderJob> thisClass = this.getClass();
        String className = thisClass.getCanonicalName();
        log.info("======= 执行定时任务：{}，当前时间为：{}，", className + ".autoCloseOrder =======",
                DateUtil.formattedDate(LocalDateTime.now(), DateUtil.DATETIME_PATTERN));

        orderService.closeOrder();
    }
}
