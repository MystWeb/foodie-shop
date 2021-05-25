package cn.myst.web.service.impl;

import cn.myst.web.mapper.CarouselMapper;
import cn.myst.web.pojo.Carousel;
import cn.myst.web.service.CarouselService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/5/25
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CarouselServiceImpl implements CarouselService {
    private final CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAll(Integer isShow) {
        if (Objects.isNull(isShow)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<Carousel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Carousel::getIsShow, isShow);
        queryWrapper.orderByDesc(Carousel::getSort);
        return carouselMapper.selectList(queryWrapper);
    }
}
