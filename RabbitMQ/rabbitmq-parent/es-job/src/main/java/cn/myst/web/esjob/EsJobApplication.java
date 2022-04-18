package cn.myst.web.esjob;

import cn.myst.web.rabbitmq.task.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableElasticJob
@ComponentScan(basePackages = {"cn.myst.web.esjob.*", "cn.myst.web.esjob.service.*", "cn.myst.web.esjob.annotation.*", "cn.myst.web.esjob.task.*"})
@SpringBootApplication
public class EsJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsJobApplication.class, args);
    }

}
