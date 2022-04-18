package cn.myst.web.esjob.service.api;

import cn.myst.web.esjob.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping("/index")
    public String index() {
        indexService.tester("123");
        return "index";
    }

}
