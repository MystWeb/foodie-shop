package cn.myst.web.tcc.demo;

import cn.myst.web.tcc.demo.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TccDemoApplicationTests {

    @Resource
    private AccountService accountService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testAccount() {
        accountService.transferAccount();
    }

}
