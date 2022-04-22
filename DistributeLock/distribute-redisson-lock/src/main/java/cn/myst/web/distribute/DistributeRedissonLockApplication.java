package cn.myst.web.distribute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:redisson.xml")
public class DistributeRedissonLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeRedissonLockApplication.class, args);
    }

}
