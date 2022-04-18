import cn.myst.web.rabbitmq.producer.Application;
import cn.myst.web.rabbitmq.producer.service.MessageStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Spring 全家桶之 Spring Boot 2.6.4（四）- Data Access（Part A JDBC）
 * 参考：https://juejin.cn/post/7077190370051751950
 *
 * @author ziming.xing
 * Create Date：2022/4/12
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest(classes = Application.class)
public class DataApplicationTests {

    private final DataSource dataSource;

    private final MessageStoreService messageStoreService;

    @Test
    public void testGetConnection() throws SQLException {
        log.info("数据源类型为：{}", dataSource.getClass());

        Connection connection = dataSource.getConnection();
        log.info("获取到到数据库连接为：{}", connection);

        connection.close();
    }
}
