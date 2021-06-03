package cn.myst.web.mapper;

import cn.myst.web.pojo.vo.CategoryVO;
import cn.myst.web.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface CategoryCustomMapper {
    List<CategoryVO> getSubCatList(@Param("rootCatId") Integer rootCatId);

    List<NewItemsVO> getSixNewItemLazy(@Param("paramsMap") Map<String, Object> paramsMap);
}