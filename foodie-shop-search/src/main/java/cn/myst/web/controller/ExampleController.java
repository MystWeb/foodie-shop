package cn.myst.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author ziming.xing
 * Create Date：2021/10/15
 */

@ApiIgnore
@Slf4j
@RestController
@RequestMapping("/search/examples")
public class ExampleController {

    @GetMapping("/hello")
    public Object hello() {
        log.info("Hello");
        return "Hello World";
    }

}
