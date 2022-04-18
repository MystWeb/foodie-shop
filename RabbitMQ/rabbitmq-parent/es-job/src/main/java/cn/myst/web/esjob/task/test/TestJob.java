package cn.myst.web.esjob.task.test;

import cn.myst.web.rabbitmq.task.annotation.ElasticJobConfig;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

@Component
@ElasticJobConfig(
			name = "cn.myst.web.esjob.task.test.TestJob",
			cron = "0/5 * * * * ?",
			description = "测试定时任务",
			overwrite = true,
			shardingTotalCount = 5
		)
public class TestJob implements SimpleJob {

	@Override
	public void execute(ShardingContext shardingContext) {
		System.err.println("执行Test job.");
	}

}
