package cn.myst.web.mycat.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"cn.myst.web"})
@SpringBootApplication
public class MycatDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycatDemoApplication.class, args);
    }

}
