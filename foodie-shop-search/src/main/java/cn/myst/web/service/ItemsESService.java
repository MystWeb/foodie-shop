package cn.myst.web.service;

import cn.myst.web.utils.PagedGridResult;

/**
 * @author ziming.xing
 * Create Date：2021/10/30
 */
public interface ItemsESService {

    /**
     * 搜索ES商品列表
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);
}
