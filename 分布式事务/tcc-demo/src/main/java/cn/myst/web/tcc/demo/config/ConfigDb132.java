package cn.myst.web.tcc.demo.config;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Configuration
@MapperScan(value = "cn.myst.web.tcc.demo.mapper.db132", sqlSessionFactoryRef = "factoryBean132")
public class ConfigDb132 {

    @Bean("db132")
    public DataSource db132() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("imooc");
        dataSource.setPassword("Imooc@123456");
        dataSource.setUrl("jdbc:mysql://192.168.73.132:3306/xa_132");

        return dataSource;
    }

    @Bean("factoryBean132")
    public SqlSessionFactoryBean factoryBean(@Qualifier("db132") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        factoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

        factoryBean.setMapperLocations(resourceResolver.getResources("mybatis/db132/*.xml"));
        return factoryBean;
    }

    @Bean("tm132")
    public PlatformTransactionManager transactionManager(@Qualifier("db132") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}