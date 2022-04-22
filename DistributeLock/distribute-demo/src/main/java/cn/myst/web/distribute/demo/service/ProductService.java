package cn.myst.web.distribute.demo.service;

import cn.myst.web.distribute.demo.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2022/4/20
 */
public interface ProductService extends IService<Product> {

    int updateBatchSelective(List<Product> list);

    int batchInsert(List<Product> list);

}
