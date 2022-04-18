package cn.myst.web.rabbitmq.producer.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2022/4/12
 */
@Configuration
@AutoConfigureAfter(value = {RabbitProducerDataSourceConfiguration.class})
public class RabbitProducerMyBatisConfiguration {
    @Resource(name = "rabbitProducerDataSource")
    private DataSource rabbitProducerDataSource;

    @Bean(name = "rabbitProducerSqlSessionFactory")
    public SqlSessionFactory rabbitProducerSqlSessionFactory(DataSource rabbitProducerDataSource) {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(rabbitProducerDataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
            SqlSessionFactory sqlSessionFactory = bean.getObject();
            if (Objects.isNull(sqlSessionFactory)) {
                return null;
            }
            sqlSessionFactory.getConfiguration().setCacheEnabled(Boolean.TRUE);
            return sqlSessionFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "rabbitProducerSqlSessionTemplate")
    public SqlSessionTemplate rabbitProducerSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
