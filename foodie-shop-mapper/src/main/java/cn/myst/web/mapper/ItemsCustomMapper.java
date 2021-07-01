package cn.myst.web.mapper;

import cn.myst.web.pojo.vo.ItemCommentVO;
import cn.myst.web.pojo.vo.SearchItemsVO;
import cn.myst.web.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author ziming.xing
 * Create Date：2021/6/19
 */
@Mapper
public interface ItemsCustomMapper {
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> paramsMap);

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> paramsMap);

    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> paramsMap);

    List<ShopcartVO> queryItemsBySpecIds(@Param("idCollection") Collection<String> idCollection);

    int decreaseItemSpecStock(@Param("specId") String specId, @Param("pendingCounts") Integer pendingCounts);
}
