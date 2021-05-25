package cn.myst.web.service;

import cn.myst.web.pojo.Carousel;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/5/25
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     */
    List<Carousel> queryAll(Integer isShow);
}
