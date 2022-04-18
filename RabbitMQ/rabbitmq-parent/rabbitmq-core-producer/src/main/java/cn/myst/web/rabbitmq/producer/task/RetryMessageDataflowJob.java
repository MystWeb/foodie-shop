package cn.myst.web.rabbitmq.producer.task;

import cn.myst.web.rabbitmq.producer.broker.RabbitMQBroker;
import cn.myst.web.rabbitmq.producer.entity.BrokerMessage;
import cn.myst.web.rabbitmq.producer.enums.EnumBrokerMessageStatus;
import cn.myst.web.rabbitmq.producer.service.MessageStoreService;
import cn.myst.web.rabbitmq.task.annotation.ElasticJobConfig;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/4/14
 */
@Slf4j
@Component
@ElasticJobConfig(
        name = "cn.myst.web.rabbitmq.producer.task.RetryMessageDataflowJob",
        cron = "0/10 * * * * ?",
        description = "可靠性投递消息补偿任务",
        overwrite = true,
        shardingTotalCount = 1
)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RetryMessageDataflowJob implements DataflowJob<BrokerMessage> {

    private final MessageStoreService messageStoreService;
    private final RabbitMQBroker rabbitMQBroker;
    public static final int MAX_RETRY_COUNT = 3;

    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        List<BrokerMessage> list = messageStoreService.fetchTimeOutMessage4Retry(EnumBrokerMessageStatus.SENDING);
        log.info("--------@@@@@ 抓取数据集合, 数量：	{} 	@@@@@@-----------", list.size());
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> list) {
        list.forEach(brokerMessage -> {
            String messageId = brokerMessage.getMessageId();

            if (brokerMessage.getTryCount() >= MAX_RETRY_COUNT) {
                messageStoreService.failure(brokerMessage.getMessageId());
                log.warn(" -----消息设置为最终失败，消息ID: {} -------", messageId);
            } else {
                //	每次重发的时候要更新一下try count字段
                messageStoreService.updateTryCount(messageId);
                // 	重发消息
                rabbitMQBroker.reliantSend(brokerMessage.getMessage());
            }
        });
    }
}
