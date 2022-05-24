package cn.myst.web.xa.demo.service.impl;

import cn.myst.web.xa.demo.domain.db131.XA131;
import cn.myst.web.xa.demo.domain.db132.XA132;
import cn.myst.web.xa.demo.mapper.db131.XA131Mapper;
import cn.myst.web.xa.demo.mapper.db132.XA132Mapper;
import cn.myst.web.xa.demo.service.XAService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class XAServiceImpl implements XAService {

    private final XA131Mapper xa131Mapper;
    private final XA132Mapper xa132Mapper;

    @Transactional(transactionManager = "xaTransaction")
    public void testXA() {
        XA131 xa131 = new XA131();
        xa131.setId(1);
        xa131.setName("xa_131");
        xa131Mapper.insert(xa131);

        XA132 xa132 = new XA132();
        xa132.setId(1);
        xa132.setName("xa_132");
        xa132Mapper.insert(xa132);

    }
}
