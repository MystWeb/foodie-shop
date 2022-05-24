package cn.myst.web.tcc.demo.mapper.db131;

import cn.myst.web.tcc.demo.domain.db131.PaymentMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Mapper
public interface PaymentMsgMapper extends BaseMapper<PaymentMsg> {
    int updateBatchSelective(List<PaymentMsg> list);

    int batchInsert(@Param("list") List<PaymentMsg> list);
}