package cn.myst.web.service.impl;

import cn.myst.web.enums.EnumCommentLevel;
import cn.myst.web.mapper.*;
import cn.myst.web.pojo.*;
import cn.myst.web.pojo.vo.CommentLevelCountsVO;
import cn.myst.web.pojo.vo.ItemCommentVO;
import cn.myst.web.pojo.vo.SearchItemsVO;
import cn.myst.web.service.ItemService;
import cn.myst.web.utils.DesensitizationUtil;
import cn.myst.web.utils.PagedGridResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private final ItemsCommentsMapper itemsCommentsMapper;
    private final ItemsCustomMapper itemsCustomMapper;

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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCountsByItemId(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, EnumCommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, EnumCommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, EnumCommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
        commentLevelCountsVO.setTotalCounts(totalCounts);
        commentLevelCountsVO.setGoodCounts(goodCounts);
        commentLevelCountsVO.setNormalCounts(normalCounts);
        commentLevelCountsVO.setBadCounts(badCounts);
        return commentLevelCountsVO;
    }

    /**
     * 根据商品id，商品评价等级查询商品评价数量
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getCommentCounts(String itemId, Integer level) {
        if (StringUtils.isBlank(itemId)) {
            return null;
        }
        LambdaQueryWrapper<ItemsComments> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ItemsComments::getItemId, itemId);
        if (Objects.nonNull(level)) {
            queryWrapper.eq(ItemsComments::getCommentLevel, level);
        }
        return itemsCommentsMapper.selectCount(queryWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);
//        return PageHelper.startPage(page, pageSize)
//                .doSelectPageInfo(() -> itemsCustomMapper.queryItemComments(map));
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> list = itemsCustomMapper.queryItemComments(map);
        list.forEach(vo -> vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname())));
        return setterPagedGrid(page, list);
    }

    private PagedGridResult setterPagedGrid(Integer page, List<?> list) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageInfo.getPageSize());
        pagedGridResult.setRecords(pageInfo.getTotal());
        return pagedGridResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsCustomMapper.searchItems(map);
        return setterPagedGrid(page, list);
    }
}
