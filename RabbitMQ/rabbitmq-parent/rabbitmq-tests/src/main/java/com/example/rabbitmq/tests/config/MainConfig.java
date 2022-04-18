package com.example.rabbitmq.tests.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ziming.xing
 * Create Date：2022/4/15
 */
@Configuration
@ComponentScan(basePackages = {"cn.myst.web.rabbitmq.*"})
public class MainConfig {
}
