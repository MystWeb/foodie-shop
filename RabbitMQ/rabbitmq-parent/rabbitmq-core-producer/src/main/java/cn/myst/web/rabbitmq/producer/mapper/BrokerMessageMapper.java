package cn.myst.web.rabbitmq.producer.mapper;

import cn.myst.web.rabbitmq.producer.entity.BrokerMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/4/12
 */
@Mapper
public interface BrokerMessageMapper {
    int updateBatchSelective(List<BrokerMessage> list);

    int batchInsert(@Param("list") List<BrokerMessage> list);

    int deleteByPrimaryKey(String messageId);

    int insert(BrokerMessage record);

    int insertSelective(BrokerMessage record);

    BrokerMessage selectByPrimaryKey(String messageId);

    int updateByPrimaryKeySelective(BrokerMessage record);

    int updateByPrimaryKey(BrokerMessage record);

    void changeBrokerMessageStatus(@Param("brokerMessageId") String brokerMessageId, @Param("brokerMessageStatus") String brokerMessageStatus, @Param("updateTime") Date updateTime);

    List<BrokerMessage> queryBrokerMessageStatus4Timeout(@Param("brokerMessageStatus") String brokerMessageStatus);

    List<BrokerMessage> queryBrokerMessageStatus(@Param("brokerMessageStatus") String brokerMessageStatus);

    int update4TryCount(@Param("brokerMessageId") String brokerMessageId, @Param("updateTime") Date updateTime);
}