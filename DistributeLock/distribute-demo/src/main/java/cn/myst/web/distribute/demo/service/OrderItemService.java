package cn.myst.web.distribute.demo.service;

import cn.myst.web.distribute.demo.domain.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/4/20
 */
public interface OrderItemService extends IService<OrderItem> {

    int updateBatchSelective(List<OrderItem> list);

    int batchInsert(List<OrderItem> list);

}
