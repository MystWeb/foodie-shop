package cn.myst.web.service.impl;

import cn.myst.web.service.BaseService;
import cn.myst.web.utils.PagedGridResult;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@Service
public class BaseServiceImpl implements BaseService {

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult setterPagedGrid(Integer page, List<?> list) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageInfo.getPageSize());
        pagedGridResult.setRecords(pageInfo.getTotal());
        return pagedGridResult;
    }
}
