package cn.myst.web.rabbitmq.producer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息的发送状态
 *
 * @author ziming.xing
 * Create Date：2022/4/12
 */
@Getter
@AllArgsConstructor
public enum EnumBrokerMessageStatus {

    // 消息发送后，待确认
    SENDING("0"),
    // 消息发送后，已确认（得到Broker的ACK）
    SEND_OK("1"),
    // 消息发送失败
    SEND_FAIL("2"),
    // 消息发送失败（根据业务需求灵活处理）例如：MQ队列满了、磁盘满了、PageCache Bypass、OS级别、IO异常……等情况
    SEND_FAIL_A_MOMENT("3");

    private final String code;
}
