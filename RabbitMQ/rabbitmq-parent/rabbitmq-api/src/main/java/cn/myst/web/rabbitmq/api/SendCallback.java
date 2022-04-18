package cn.myst.web.rabbitmq.api;

/**
 * 回调函数处理
 *
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public interface SendCallback {

    void onSuccess();

    void onFailure();
}
