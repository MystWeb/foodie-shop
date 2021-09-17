package cn.myst.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ziming.xing
 * Create Date：2021/8/24
 */
// 去除SpringSecurity权限自动装配（Springboot 2.3+，需要多排除一个：ManagementWebSecurityAutoConfiguration.class）
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@SpringBootApplication
// 扫描所有包以及相关组件包
@ComponentScan(basePackages = {"cn.myst.web", "org.n3r.idworker"})
public class SSOApplication {

    public static void main(String[] args) {
        SpringApplication.run(SSOApplication.class, args);
    }

}