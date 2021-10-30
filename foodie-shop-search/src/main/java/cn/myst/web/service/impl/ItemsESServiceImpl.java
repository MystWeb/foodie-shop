package cn.myst.web.service.impl;

import cn.myst.web.service.ItemsESService;
import cn.myst.web.utils.PagedGridResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ziming.xing
 * Create Date：2021/10/30
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemsESServiceImpl implements ItemsESService {

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        return null;
    }
}
