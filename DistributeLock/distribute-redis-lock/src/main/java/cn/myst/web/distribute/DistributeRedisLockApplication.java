package cn.myst.web.distribute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DistributeRedisLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeRedisLockApplication.class, args);
    }

}
