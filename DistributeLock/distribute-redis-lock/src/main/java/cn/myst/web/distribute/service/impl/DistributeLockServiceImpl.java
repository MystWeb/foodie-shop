package cn.myst.web.distribute.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import cn.myst.web.distribute.mapper.DistributeLockMapper;
import cn.myst.web.distribute.domain.DistributeLock;
import cn.myst.web.distribute.service.DistributeLockService;

/**
 * @author ziming.xing
 * Create Date：2022/4/21
 */
@Service
public class DistributeLockServiceImpl extends ServiceImpl<DistributeLockMapper, DistributeLock> implements DistributeLockService {

    @Override
    public int updateBatchSelective(List<DistributeLock> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<DistributeLock> list) {
        return baseMapper.batchInsert(list);
    }
}

