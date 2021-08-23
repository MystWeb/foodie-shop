package cn.myst.web.controller.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ziming.xing
 * Create Date：2021/8/20
 */
//@ApiIgnore
@Slf4j
@RestController
@RequestMapping("/examples")
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ExampleController {

    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", "new user");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
//        session.removeAttribute("userInfo");
        return "ok";
    }

    @GetMapping("/hello")
    public Object hello() {
        log.info("Hello");
        return "Hello World";
    }

}
