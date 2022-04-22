package cn.myst.web.distribute.service.impl;

import cn.myst.web.distribute.lock.RedisLock;
import cn.myst.web.distribute.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author ziming.xing
 * Create Date：2022/4/21
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SchedulerServiceImpl implements SchedulerService {

    private final RedisTemplate redisTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    @Override
    public void sendSms() {
        try (RedisLock redisLock = new RedisLock(redisTemplate, "autoSms", 30)) {
            if (redisLock.getLock()) {
                log.info("向13811112222发送短信！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
