package cn.myst.web.service.impl;

import cn.myst.web.mapper.ItemsImgMapper;
import cn.myst.web.mapper.ItemsMapper;
import cn.myst.web.mapper.ItemsParamMapper;
import cn.myst.web.mapper.ItemsSpecMapper;
import cn.myst.web.pojo.Items;
import cn.myst.web.pojo.ItemsImg;
import cn.myst.web.pojo.ItemsParam;
import cn.myst.web.pojo.ItemsSpec;
import cn.myst.web.service.ItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/6/15
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemServiceImpl implements ItemService {
    private final ItemsMapper itemsMapper;
    private final ItemsImgMapper itemsImgMapper;
    private final ItemsSpecMapper itemsSpecMapper;
    private final ItemsParamMapper itemsParamMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemByItemId(String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return null;
        }
        return itemsMapper.selectById(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgListByItemId(String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ItemsImg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ItemsImg::getItemId, itemId);
        return itemsImgMapper.selectList(queryWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecListByItemId(String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ItemsSpec> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ItemsSpec::getItemId, itemId);
        return itemsSpecMapper.selectList(queryWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParamByItemId(String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return null;
        }
        LambdaQueryWrapper<ItemsParam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ItemsParam::getItemId, itemId);
        return itemsParamMapper.selectOne(queryWrapper);
    }
}
