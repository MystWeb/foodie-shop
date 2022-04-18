package cn.myst.web.rabbitmq.producer.config.database;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author ziming.xing
 * Create Date：2022/4/12
 */
@Slf4j
@Getter
@ToString
@Configuration
//@PropertySource("classpath:config/rabbit-producer-message-" + "${spring.profiles.active}" + ".properties")
@PropertySource("classpath:config/rabbit-producer-message-dev.properties")
public class RabbitProducerDataSourceConfiguration {

    @Value("${rabbit.producer.druid.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "rabbitProducerDataSource")
    @Primary
    @ConfigurationProperties(prefix = "rabbit.producer.druid.jdbc")
    public DataSource rabbitProducerDataSource() throws SQLException {
        DataSource rabbitProducerDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        log.info("============= rabbitProducerDataSource : {} ================", rabbitProducerDataSource);
        return rabbitProducerDataSource;
    }

    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    public DataSource primaryDataSource() {
        return primaryDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
