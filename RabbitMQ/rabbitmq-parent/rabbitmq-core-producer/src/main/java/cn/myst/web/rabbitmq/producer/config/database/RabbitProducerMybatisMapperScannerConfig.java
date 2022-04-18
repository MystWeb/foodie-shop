package cn.myst.web.rabbitmq.producer.config.database;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ziming.xing
 * Create Date：2022/4/12
 */
@Configuration
@AutoConfigureAfter(RabbitProducerDataSourceConfiguration.class)
public class RabbitProducerMybatisMapperScannerConfig {

    @Bean(name = "rabbitProducerMapperScannerConfigurer")
    public MapperScannerConfigurer rabbitProducerMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("rabbitProducerSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("cn.myst.web.rabbitmq.producer.mapper");
        return mapperScannerConfigurer;
    }
}