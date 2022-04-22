package cn.myst.web.distribute.demo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import cn.myst.web.distribute.demo.domain.OrderItem;
import cn.myst.web.distribute.demo.mapper.OrderItemMapper;
import cn.myst.web.distribute.demo.service.OrderItemService;
/**
 * @author ziming.xing
 * Create Date：2022/4/20
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService{

    @Override
    public int updateBatchSelective(List<OrderItem> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<OrderItem> list) {
        return baseMapper.batchInsert(list);
    }
}
