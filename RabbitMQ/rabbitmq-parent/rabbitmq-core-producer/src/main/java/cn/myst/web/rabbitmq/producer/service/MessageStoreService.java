package cn.myst.web.rabbitmq.producer.service;

import cn.myst.web.rabbitmq.producer.entity.BrokerMessage;
import cn.myst.web.rabbitmq.producer.enums.EnumBrokerMessageStatus;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/4/12
 */
public interface MessageStoreService {
    int insert(BrokerMessage brokerMessage);

    BrokerMessage selectByMessageId(String messageId);

    void success(String messageId);

    void failure(String messageId);

    List<BrokerMessage> fetchTimeOutMessage4Retry(EnumBrokerMessageStatus brokerMessageStatus);

    int updateTryCount(String brokerMessageId);
}
