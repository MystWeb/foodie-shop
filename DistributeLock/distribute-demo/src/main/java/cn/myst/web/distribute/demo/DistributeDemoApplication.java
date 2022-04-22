package cn.myst.web.distribute.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"cn.myst.web"})
@SpringBootApplication
public class DistributeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeDemoApplication.class, args);
    }

}
