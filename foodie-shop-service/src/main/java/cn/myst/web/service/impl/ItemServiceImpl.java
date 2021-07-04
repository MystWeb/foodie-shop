package cn.myst.web.service.impl;

import cn.myst.web.enums.EnumCommentLevel;
import cn.myst.web.enums.EnumOrder;
import cn.myst.web.enums.EnumYesOrNo;
import cn.myst.web.exception.BusinessException;
import cn.myst.web.mapper.*;
import cn.myst.web.pojo.*;
import cn.myst.web.pojo.vo.CommentLevelCountsVO;
import cn.myst.web.pojo.vo.ItemCommentVO;
import cn.myst.web.pojo.vo.SearchItemsVO;
import cn.myst.web.pojo.vo.ShopcartVO;
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
    @Override
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
    public PagedGridResult setterPagedGrid(Integer page, List<?> list) {
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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        if (Objects.isNull(catId)) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsCustomMapper.searchItemsByThirdCat(map);
        return setterPagedGrid(page, list);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {
        if (StringUtils.isBlank(specIds)) {
            return Collections.emptyList();
        }
        String[] ids = specIds.split(",");
        ArrayList<String> specIdsList = new ArrayList<>();
        // 性能最高的转换方式
        Collections.addAll(specIdsList, ids);
        return itemsCustomMapper.queryItemsBySpecIds(specIdsList);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsSpec queryItemSpecBySpecId(String specId) {
        if (StringUtils.isBlank(specId)) {
            return null;
        }
        return itemsSpecMapper.selectById(specId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryItemMainImgByItemId(String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return null;
        }
        LambdaQueryWrapper<ItemsImg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ItemsImg::getItemId, itemId);
        queryWrapper.eq(ItemsImg::getIsMain, EnumYesOrNo.YES.type);
        ItemsImg itemsImg = itemsImgMapper.selectOne(queryWrapper);
        return itemsImg != null ? itemsImg.getUrl() : null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void decreaseItemSpecStock(String specId, int buyCounts) {
        if (StringUtils.isBlank(specId) || buyCounts == 0) {
            throw new BusinessException(EnumOrder.ORDER_CREATION_FAILED_BY_INSUFFICIENT_INVENTORY.zh);
        }
        // synchronized：集群下无用，因为集群时还是会存在资源共享；性能低下；（不推荐）
        // 锁数据库：导致数据库性能低下（不推荐）
        // 分布式锁：zookeeper、redis实现分布式锁（推荐）
        // 乐观锁：单体架构时通过SQL语句去做数据更新（临时方案）

        // lockUtil.getLock(); # 加锁
        // 1. 查询库存
       /* int stock = 10;
        // 2. 判断库存，是否能够减到0以下
        if (stock - buyCounts < 0) {
            // 提示库存不够
            // 10（库存数） - 3（线程1-购买数量） - 3（线程2-购买数量） - 5（线程3-购买数量） = -1（超卖）
        }*/

        // lockUtil.unLock(); # 解锁

        int result = itemsCustomMapper.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new BusinessException(EnumOrder.ORDER_CREATION_FAILED_BY_INSUFFICIENT_INVENTORY.zh);
        }
    }
}
