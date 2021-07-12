package cn.myst.web.task;

import cn.myst.web.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author ziming.xing
 * Create Date：2021/7/12
 */
@Slf4j
@Component
@Configuration
public class OrderJob {

    @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrder() {
        log.info("执行定时任务，当前时间为：" + DateUtil.formattedDate(LocalDateTime.now(), DateUtil.DATETIME_PATTERN));
    }
}
