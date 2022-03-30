package cn.myst.web.controller.example;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziming.xing
 * Create Date：2021/8/24
 */
@Hidden
@Slf4j
@RestController
@RequestMapping("/sso/example")
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExampleController {

    @GetMapping("/hello")
    public Object hello() {
        log.info("Hello");
        return "Hello World";
    }
}
