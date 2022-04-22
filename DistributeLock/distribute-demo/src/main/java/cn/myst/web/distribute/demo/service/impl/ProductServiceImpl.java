package cn.myst.web.distribute.demo.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import cn.myst.web.distribute.demo.domain.Product;
import cn.myst.web.distribute.demo.mapper.ProductMapper;
import cn.myst.web.distribute.demo.service.ProductService;
/**
 * @author ziming.xing
 * Create Date：2022/4/20
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService{

    @Override
    public int updateBatchSelective(List<Product> list) {
        return baseMapper.updateBatchSelective(list);
    }
    @Override
    public int batchInsert(List<Product> list) {
        return baseMapper.batchInsert(list);
    }
}
