package cn.myst.web.service;

import cn.myst.web.pojo.Items;
import cn.myst.web.pojo.ItemsImg;
import cn.myst.web.pojo.ItemsParam;
import cn.myst.web.pojo.ItemsSpec;

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

}
