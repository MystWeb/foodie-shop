package cn.myst.web.xa.demo.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.io.IOException;

/**
 * @author ziming.xing
 * Create Date：2022/5/24
 */
@Configuration
@MapperScan(value = "cn.myst.web.xa.demo.mapper.db131", sqlSessionFactoryRef = "sqlSessionFactoryBean131")
public class ConfigDb131 {

    @Bean
    public DataSource db131() {
        MysqlXADataSource xaDataSource = new MysqlXADataSource();
        xaDataSource.setUser("imooc");
        xaDataSource.setPassword("Imooc@123456");
        xaDataSource.setUrl("jdbc:mysql://192.168.73.131:3306/xa_131");

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);

        return atomikosDataSourceBean;
    }

    @Bean("sqlSessionFactoryBean131")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("db131") DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourceResolver.getResources("mapper/db131/*.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean("xaTransaction")
    public JtaTransactionManager jtaTransactionManager() {
        UserTransaction userTransaction = new UserTransactionImp();
        UserTransactionManager userTransactionManager = new UserTransactionManager();

        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}