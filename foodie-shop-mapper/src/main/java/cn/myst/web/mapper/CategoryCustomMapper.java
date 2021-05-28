package cn.myst.web.mapper;

import cn.myst.web.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/5/18
 */
@Mapper
public interface CategoryCustomMapper {
    List<CategoryVO> getSubCatList(@Param("rootCatId") Integer rootCatId);
}