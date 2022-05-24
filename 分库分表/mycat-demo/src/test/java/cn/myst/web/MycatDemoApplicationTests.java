package cn.myst.web;

import cn.myst.web.mycat.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class MycatDemoApplicationTests {

    private final UserService userService;

    @Test
    void contextLoads() {
    }


    @Test
    public void testUser() {
        userService.testUser();
    }


}
