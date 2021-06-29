package cn.myst.web.service.impl;

import cn.myst.web.enums.EnumType;
import cn.myst.web.mapper.CategoryCustomMapper;
import cn.myst.web.mapper.CategoryMapper;
import cn.myst.web.pojo.Category;
import cn.myst.web.pojo.vo.CategoryVO;
import cn.myst.web.pojo.vo.NewItemsVO;
import cn.myst.web.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author ziming.xing
 * Create Date：2021/5/28
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryCustomMapper categoryCustomMapper;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getType, EnumType.LARGE_CLASSIFICATION.type);
        return categoryMapper.selectList(queryWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> querySubCatByRootCatId(Integer rootCatId) {
        if (Objects.isNull(rootCatId)) {
            return Collections.emptyList();
        }
        return categoryCustomMapper.getSubCatList(rootCatId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> querySixNewItemLazyByRootCatId(Integer rootCatId) {
        if (Objects.isNull(rootCatId)) {
            return Collections.emptyList();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return categoryCustomMapper.getSixNewItemLazy(map);
    }
}
