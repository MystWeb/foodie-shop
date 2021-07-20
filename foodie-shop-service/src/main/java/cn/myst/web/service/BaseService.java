package cn.myst.web.service;

import cn.myst.web.utils.PagedGridResult;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
public interface BaseService {

    /**
     * 设置分页表格结果对象
     */
    PagedGridResult setterPagedGrid(Integer page, List<?> list);
}
