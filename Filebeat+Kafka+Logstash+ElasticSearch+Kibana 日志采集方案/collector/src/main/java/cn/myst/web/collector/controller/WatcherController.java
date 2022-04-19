package cn.myst.web.collector.controller;

import cn.myst.web.collector.entity.AccurateWatcherMessage;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziming.xing
 * Create Date：2022/4/18
 */
@Slf4j
@RestController
public class WatcherController {

    @RequestMapping(value = "/accurateWatch")
    public String watch(@RequestBody AccurateWatcherMessage accurateWatcherMessage) {
        String ret = JSON.toJSONString(accurateWatcherMessage);
        System.err.println("----告警内容----:" + ret);
        return "is watched" + ret;
    }

}
