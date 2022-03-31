package cn.myst.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ziming.xing
 * Create Date：2022/3/30
 */
@SpringBootApplication
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"cn.myst.web", "org.n3r.idworker"})
public class OSSApplication {
    public static void main(String[] args) {
        SpringApplication.run(OSSApplication.class, args);
    }
}
