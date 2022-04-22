package cn.myst.web.distribute.service;

/**
 * @author ziming.xing
 * Create Date：2022/4/21
 */
public interface SchedulerService {
    /**
     * 定时任务，自动发送短信
     */
    void sendSms();
}
