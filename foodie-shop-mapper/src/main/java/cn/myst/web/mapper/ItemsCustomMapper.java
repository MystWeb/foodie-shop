package cn.myst.web.mapper;

import cn.myst.web.pojo.vo.ItemCommentVO;
import cn.myst.web.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
