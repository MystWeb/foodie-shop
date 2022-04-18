-- 表 broker_message.broker_message 结构
CREATE TABLE IF NOT EXISTS `broker_message`
(
    `message_id`  varchar(128)  NOT NULL COMMENT '消息ID',
    `message`     varchar(4000) NULL     DEFAULT NULL COMMENT '消息内容',
    `try_count`   int           NULL     DEFAULT 0 COMMENT '重试次数',
    `status`      varchar(10)   NULL     DEFAULT '' COMMENT '消息状态',
    `next_retry`  timestamp     NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '下一次重试时间',
    `create_time` timestamp     NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
    `update_time` timestamp     NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
    PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB COMMENT = '消息队列日志表';