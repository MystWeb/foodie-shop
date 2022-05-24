package cn.myst.web.sharding.jdbc.demo.sharding;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
public class CustomSharding implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> preciseShardingValue) {
        Long id = preciseShardingValue.getValue();

        long mode = id % availableTargetNames.size();
        String[] strings = availableTargetNames.toArray(new String[0]);
        mode = Math.abs(mode);

        System.out.println(strings[0] + "---------" + strings[1]);
        System.out.println("mode=" + mode);
        return strings[(int) mode];
    }
}
