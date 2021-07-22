package cn.myst.web.mapper;

import cn.myst.web.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface ItemsCommentsCustomMapper {

    int saveComments(@Param("paramsMap") Map<String, Object> paramsMap);

    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> paramsMap);

}