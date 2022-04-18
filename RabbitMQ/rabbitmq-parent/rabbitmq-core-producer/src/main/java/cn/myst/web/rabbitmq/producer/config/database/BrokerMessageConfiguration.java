package cn.myst.web.rabbitmq.producer.config.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * 帮我执行SQL脚本
 * 帮我进行数据库表结构的创建
 *
 * @author ziming.xing
 * Create Date：2022/4/12
 */
@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BrokerMessageConfiguration {

    private final DataSource rabbitProducerDataSource;

    @Value("classpath:/db/rabbit-producer-message-schema.sql")
    private Resource schemaScript;

    @Bean
    public DataSourceInitializer initDataSourceInitializer() {
        log.info("--------------rabbitProducerDataSource-----------:{}", rabbitProducerDataSource);
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(rabbitProducerDataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaScript);
        return populator;
    }
}
