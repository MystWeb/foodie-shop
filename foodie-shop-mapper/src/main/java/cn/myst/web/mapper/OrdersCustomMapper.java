package cn.myst.web.mapper;

import cn.myst.web.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ziming.xing
 * Create Date：2021/7/20
 */
@Mapper
public interface OrdersCustomMapper {

    List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> paramsMap);

}
