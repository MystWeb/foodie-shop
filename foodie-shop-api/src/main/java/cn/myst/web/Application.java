package cn.myst.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author ziming.xing
 * Create Date：2021/5/12
 */
@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
//@MapperScan(basePackages = "cn.myst.web.**.mapper")
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"cn.myst.web", "org.n3r.idworker"})
//@EnableTransactionManagement
@EnableScheduling       // 开启定时任务
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}