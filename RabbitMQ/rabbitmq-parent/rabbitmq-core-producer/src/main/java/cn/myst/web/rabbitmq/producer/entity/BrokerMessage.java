package cn.myst.web.rabbitmq.producer.entity;

import cn.myst.web.rabbitmq.api.entity.Message;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息队列日志表
 *
 * @author ziming.xing
 * Create Date：2022/4/12
 */
@Data
public class BrokerMessage implements Serializable {
    /**
     * 消息ID
     */
    private String messageId;

    /**
     * 消息内容
     */
    private Message message;

    /**
     * 重试次数
     */
    private Integer tryCount = 0;

    /**
     * 消息状态
     */
    private String status;

    /**
     * 下一次重试时间
     */
    private Date nextRetry;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_MESSAGE_ID = "message_id";

    public static final String COL_MESSAGE = "message";

    public static final String COL_TRY_COUNT = "try_count";

    public static final String COL_STATUS = "status";

    public static final String COL_NEXT_RETRY = "next_retry";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}