package cn.myst.web.service;

import cn.myst.web.pojo.Items;
import cn.myst.web.pojo.ItemsImg;
import cn.myst.web.pojo.ItemsParam;
import cn.myst.web.pojo.ItemsSpec;
import cn.myst.web.pojo.vo.CommentLevelCountsVO;
import cn.myst.web.pojo.vo.ShopcartVO;
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
     * 根据商品id，商品评价等级查询商品评价数量
     */
    Integer getCommentCounts(String itemId, Integer level);

    /**
     * 根据商品id商品评价等级查询商品的评价
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据分类id搜索商品列表
     */
    PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格id查询最新的购物车中商品数据（用户刷新渲染购物车中的商品数据）
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格id查询规格的具体信息
     */
    ItemsSpec queryItemSpecBySpecId(String specId);

    /**
     * 根据商品id查询商品图片主图url
     */
    String queryItemMainImgByItemId(String itemId);

    /**
     * 减少库存
     */
    void decreaseItemSpecStock(String specId, int buyCounts);
}
