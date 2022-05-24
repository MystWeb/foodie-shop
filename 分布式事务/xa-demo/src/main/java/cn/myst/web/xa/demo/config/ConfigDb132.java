package cn.myst.web.xa.demo.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Configuration
@MapperScan(value = "cn.myst.web.xa.demo.mapper.db132", sqlSessionFactoryRef = "sqlSessionFactoryBean132")
public class ConfigDb132 {

    @Bean
    public DataSource db132() {
        MysqlXADataSource xaDataSource = new MysqlXADataSource();
        xaDataSource.setUser("imooc");
        xaDataSource.setPassword("Imooc@123456");
        xaDataSource.setUrl("jdbc:mysql://192.168.73.132:3306/xa_132");

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);

        return atomikosDataSourceBean;
    }

    @Bean("sqlSessionFactoryBean132")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("db132") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourceResolver.getResources("mapper/db132/*.xml"));
        return sqlSessionFactoryBean;
    }

}
