package cn.myst.web.rabbitmq.producer.service.impl;

import cn.myst.web.rabbitmq.producer.entity.BrokerMessage;
import cn.myst.web.rabbitmq.producer.enums.EnumBrokerMessageStatus;
import cn.myst.web.rabbitmq.producer.mapper.BrokerMessageMapper;
import cn.myst.web.rabbitmq.producer.service.MessageStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2022/4/12
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MessageStoreServiceImpl implements MessageStoreService {

    private final BrokerMessageMapper brokerMessageMapper;

    @Override
    public int insert(BrokerMessage brokerMessage) {
        if (Objects.isNull(brokerMessage)) {
            return 0;
        }
        return brokerMessageMapper.insert(brokerMessage);
    }

    @Override
    public BrokerMessage selectByMessageId(String messageId) {
        if (StringUtils.isBlank(messageId)) {
            return null;
        }
        return brokerMessageMapper.selectByPrimaryKey(messageId);
    }

    @Override
    public void success(String messageId) {
        if (StringUtils.isBlank(messageId)) {
            return;
        }
        /*
        BrokerMessage brokerMessage = new BrokerMessage();
        brokerMessage.setMessageId(messageId);
        brokerMessage.setStatus(EnumBrokerMessageStatus.SEND_OK.getCode());
        brokerMessage.setUpdateTime(Date.from(Instant.now()));
        brokerMessageMapper.updateById(brokerMessage);
        */
        brokerMessageMapper.changeBrokerMessageStatus(messageId,
                EnumBrokerMessageStatus.SEND_OK.getCode(),
                Date.from(Instant.now()));
    }

    @Override
    public void failure(String messageId) {
        if (StringUtils.isBlank(messageId)) {
            return;
        }
        /*
        BrokerMessage brokerMessage = new BrokerMessage();
        brokerMessage.setMessageId(messageId);
        brokerMessage.setStatus(EnumBrokerMessageStatus.SEND_FAIL.getCode());
        brokerMessage.setUpdateTime(Date.from(Instant.now()));
        brokerMessageMapper.updateById(brokerMessage);
        */
        brokerMessageMapper.changeBrokerMessageStatus(messageId,
                EnumBrokerMessageStatus.SEND_FAIL.getCode(),
                new Date());
    }

    @Override
    public List<BrokerMessage> fetchTimeOutMessage4Retry(EnumBrokerMessageStatus brokerMessageStatus) {
        if (Objects.isNull(brokerMessageStatus)) {
            return Collections.emptyList();
        }
        /*
        // Mapper queryBrokerMessageStatus4Timeout
        LambdaQueryWrapper<BrokerMessage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BrokerMessage::getStatus, brokerMessageStatus.getCode())
//                .apply("next_retry < sysdate()")
                .lt(BrokerMessage::getNextRetry, LocalDateTime.now());
        return brokerMessageMapper.selectList(lambdaQueryWrapper);
        */
        return brokerMessageMapper.queryBrokerMessageStatus4Timeout(brokerMessageStatus.getCode());
    }

    @Override
    public int updateTryCount(String brokerMessageId) {
        if (StringUtils.isBlank(brokerMessageId)) {
            return 0;
        }
        /*
        // Mapper update4TryCount
        LambdaUpdateWrapper<BrokerMessage> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        String sql = "try_count = try_count + " + 1;
        lambdaUpdateWrapper.setSql(sql)
                .eq(BrokerMessage::getMessageId, brokerMessageId)
        ;
        return brokerMessageMapper.update(null, lambdaUpdateWrapper);
        */
        return brokerMessageMapper.update4TryCount(brokerMessageId, Date.from(Instant.now()));
    }

}