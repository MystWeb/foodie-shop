package cn.myst.web.tcc.demo.service;

import cn.myst.web.tcc.demo.domain.db131.Account1;
import cn.myst.web.tcc.demo.domain.db132.Account2;
import cn.myst.web.tcc.demo.mapper.db131.Account1Mapper;
import cn.myst.web.tcc.demo.mapper.db132.Account2Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Service
public class AccountService {
    @Resource
    private Account1Mapper account1Mapper;
    @Resource
    private Account2Mapper account2Mapper;

    @Transactional(transactionManager = "tm131", rollbackFor = Exception.class)
    public void transferAccount() {
        Account1 account1 = account1Mapper.selectById(1);
        account1.setBalance(account1.getBalance().subtract(new BigDecimal(200)));
        account1Mapper.updateById(account1);

        Account2 account2 = account2Mapper.selectById(2);
        account2.setBalance(account2.getBalance().add(new BigDecimal(200)));
        account2Mapper.updateById(account2);

        try {
            int i = 1 / 0;
        } catch (Exception e) {

            try {
                Account2 account2ById = account2Mapper.selectById(2);
                account2ById.setBalance(account2ById.getBalance().subtract(new BigDecimal(200)));
                account2Mapper.updateById(account2ById);
            } catch (Exception e1) {

            }
            throw e;
        }
    }
}