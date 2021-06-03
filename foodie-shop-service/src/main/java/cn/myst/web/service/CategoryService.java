package cn.myst.web.service;

import cn.myst.web.pojo.Category;
import cn.myst.web.pojo.vo.CategoryVO;
import cn.myst.web.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/5/28
 */
public interface CategoryService {
    /**
     * 查询所有一级分类
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id查询子分类信息
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     */
    List<NewItemsVO> getSixNewItemLazy(Integer rootCatId);
}
