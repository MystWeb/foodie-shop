package cn.myst.web.esjob.listener;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleJobListener implements ElasticJobListener {

//	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleJobListener.class);
	
	@Override
	public void beforeJobExecuted(ShardingContexts shardingContexts) {
//		LOGGER.info("-----------------执行任务之前：{}", JSON.toJSONString(shardingContexts));
		log.info("-----------------执行任务之前：{}", JSON.toJSONString(shardingContexts));
	}

	@Override
	public void afterJobExecuted(ShardingContexts shardingContexts) {
//		LOGGER.info("-----------------执行任务之后：{}", JSON.toJSONString(shardingContexts));
		log.info("-----------------执行任务之后：{}", JSON.toJSONString(shardingContexts));
	}
	
}