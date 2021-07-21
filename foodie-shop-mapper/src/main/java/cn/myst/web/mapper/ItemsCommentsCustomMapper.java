package cn.myst.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface ItemsCommentsCustomMapper {

    int saveComments(@Param("paramsMap") Map<String, Object> paramsMap);

}