package cn.myst.web.distribute.controller;

import cn.myst.web.distribute.domain.DistributeLock;
import cn.myst.web.distribute.mapper.DistributeLockMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ziming.xing
 * Create Date：2022/4/21
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DistributeLockController {

    @Resource
    private DistributeLockMapper distributeLockMapper;

    @RequestMapping("singleLock")
    @Transactional(rollbackFor = Exception.class)
    public String singleLock() throws Exception {
        log.info("我进入了方法！");
        LambdaQueryWrapper<DistributeLock> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // Mysql InnoDB 排他锁：select ... for update
        String sql = "for update";
        lambdaQueryWrapper.eq(DistributeLock::getBusinessCode, "single")
                .last(sql);
        DistributeLock distributeLock = distributeLockMapper.selectOne(lambdaQueryWrapper);
        if (distributeLock == null) throw new Exception("分布式锁找不到");
        log.info("我进入了锁！");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "我已经执行完成！";
    }
}
