package cn.myst.web.rabbitmq.api.constant;

/**
 * @author ziming.xing
 * Create Date：2022/4/11
 */
public interface MessageType {
    /**
     * 迅速消息：不需要保障消息的可靠性, 也不需要做confirm确认
     */
    String RAPID = "0";

    /**
     * 确认消息：不需要保障消息的可靠性，但是会做消息的confirm确认
     */
    String CONFIRM = "1";

    /**
     * 可靠性消息： 一定要保障消息的100%可靠性投递，不允许有任何消息的丢失
     * PS: 保障数据库和所发的消息是原子性的（最终一致的）
     */
    String RELIANT = "2";
}
