package cn.myst.web.service.center.impl;

import cn.myst.web.enums.EnumBaseException;
import cn.myst.web.exception.BusinessException;
import cn.myst.web.mapper.OrdersCustomMapper;
import cn.myst.web.pojo.vo.MyOrdersVO;
import cn.myst.web.service.BaseService;
import cn.myst.web.service.center.MyOrdersService;
import cn.myst.web.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MyOrdersServiceImpl implements MyOrdersService {
    private final OrdersCustomMapper ordersCustomMapper;
    private final BaseService baseService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException(EnumBaseException.INCORRECT_REQUEST_PARAMETER.zh);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (Objects.nonNull(orderStatus)) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> list = ordersCustomMapper.queryMyOrders(map);
        return baseService.setterPagedGrid(page, list);
    }
}
