package cn.myst.web.service;

import cn.myst.web.entity.base.BasePagingQuery;
import cn.myst.web.pojo.Items;
import cn.myst.web.pojo.ItemsImg;
import cn.myst.web.pojo.ItemsParam;
import cn.myst.web.pojo.ItemsSpec;
import cn.myst.web.pojo.vo.CommentLevelCountsVO;
import cn.myst.web.utils.PagedGridResult;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/6/15
 */
public interface ItemService {
    /**
     * 根据商品id查询商品详情
     */
    Items queryItemByItemId(String itemId);

    /**
     * 根据商品id查询商品图片列表
     */
    List<ItemsImg> queryItemImgListByItemId(String itemId);

    /**
     * 根据商品id查询商品规格列表
     */
    List<ItemsSpec> queryItemSpecListByItemId(String itemId);

    /**
     * 根据商品id查询商品参数
     */
    ItemsParam queryItemParamByItemId(String itemId);

    /**
     * 根据商品id查询商品的评价数量
     */
    CommentLevelCountsVO queryCommentCountsByItemId(String itemId);

    /**
     * 根据商品id，商品评价等级查询商品的评价
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, BasePagingQuery query);

}
