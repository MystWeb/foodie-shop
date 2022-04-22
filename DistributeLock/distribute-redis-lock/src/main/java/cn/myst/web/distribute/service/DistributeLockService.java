package cn.myst.web.distribute.service;

import java.util.List;
import cn.myst.web.distribute.domain.DistributeLock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ziming.xing
 * Create Date：2022/4/21
 */
public interface DistributeLockService extends IService<DistributeLock> {


    int updateBatchSelective(List<DistributeLock> list);

    int batchInsert(List<DistributeLock> list);

}

