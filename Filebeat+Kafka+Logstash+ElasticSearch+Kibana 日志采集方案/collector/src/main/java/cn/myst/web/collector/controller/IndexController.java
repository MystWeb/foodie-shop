package cn.myst.web.collector.controller;

import cn.myst.web.collector.utils.InputMDC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziming.xing
 * Create Date：2022/4/18
 */
@Slf4j
@RestController
public class IndexController {
    /**
     * log4j2 日志输出格式
     * <p>
     * [%d{yyyy-MM-dd'T'HH:mm:ss.SSSZZ}]
     * [%level{length=5}]
     * [%thread-%tid]
     * [%logger]
     * [%X{hostName}]
     * [%X{ip}]
     * [%X{applicationName}]
     * [%F,%L,%C,%M]
     * [%m] ## '%ex'%n
     * -----------------------------------------------
     * [2019-09-18T14:42:51.451+08:00]
     * [INFO]
     * [main-1]
     * [org.springframework.boot.web.embedded.tomcat.TomcatWebServer]
     * []
     * []
     * []
     * [TomcatWebServer.java,90,org.springframework.boot.web.embedded.tomcat.TomcatWebServer,initialize]
     * [Tomcat initialized with port(s): 8001 (http)] ## ''
     * <p>
     * logstash grok 表达式
     * * NOTSPACE 表示不能为空，例如 currentDateTime 不能为空
     * * DATA 则表示数据可以为空
     * <p>
     * ["message",
     * "\[%{NOTSPACE:currentDateTime}\]
     * \[%{NOTSPACE:level}\]
     * \[%{NOTSPACE:thread-id}\]
     * \[%{NOTSPACE:class}\]
     * \[%{DATA:hostName}\]
     * \[%{DATA:ip}\]
     * \[%{DATA:applicationName}\]
     * \[%{DATA:location}\]
     * \[%{DATA:messageInfo}\]
     * ## (\'\'|%{QUOTEDSTRING:throwable})"]
     */
    @GetMapping(value = "/index")
    public String index() {
        InputMDC.putMDC();

        log.info("我是一条info日志");

        log.warn("我是一条warn日志");

        log.error("我是一条error日志");

        return "idx";
    }


    @GetMapping(value = "/err")
    public String err() {
        InputMDC.putMDC();
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            log.error("算术异常", e);
        }
        return "err";
    }

}
