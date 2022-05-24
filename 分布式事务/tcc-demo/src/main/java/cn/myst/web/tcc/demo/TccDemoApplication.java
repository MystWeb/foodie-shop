package cn.myst.web.tcc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TccDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccDemoApplication.class, args);
    }

}
