package cn.myst.web.xa.demo;

import cn.myst.web.xa.demo.service.XAService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class XaDemoApplicationTests {

    private final XAService xaService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testXA() {

        xaService.testXA();

    }

}
