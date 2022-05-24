package cn.myst.web.sharding.jdbc.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ImportResource("classpath*:sharding-jdbc.xml")
@MapperScan(basePackages = {"cn.myst.web"})
@SpringBootApplication
public class ShardingJdbcDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcDemoApplication.class, args);
    }

}
