package cn.myst.web;

import cn.myst.web.es.pojo.Stu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 不建议使用 ElasticsearchRestTemplate 对索引进行管理（创建索引，更新映射，删除索引）
 * 索引就像数据库或数据库中的表，我们平时是不会通过java代码频繁的去创建/修改数据库或者数据表
 * 我们只会针对数据做CRUD的操作
 * 在ES中也是同理，尽量使用 ElasticsearchRestTemplate 对文档数据做CRUD的操作
 * 1、属性（FiledType）类型不灵活
 * 2、主分片与副分片数无法设置
 *
 * @author ziming.xing
 * Create Date：2021/9/16
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SearchApplication.class)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class ESTest {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void createIndexStu() {
        Stu stu = Stu.builder()
                .stuId(1002L)
                .name("spider man")
                .age(18)
                .money(18.8F)
                .sign("i am spider man")
                .description("I wish am spider man")
                .build();
        assertNotNull(stu);
        elasticsearchRestTemplate.save(stu);
    }

    @Test
    void deleteIndexStu() {
        // 根据ID删除-indexName默认取@Document中的indexName
        elasticsearchRestTemplate.delete("1001", Stu.class);
        // 根据ID删除-indexName动态指定
        elasticsearchRestTemplate.delete("1002", IndexCoordinates.of("stu"));
        // 删除索引
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Stu.class);
        assertNotNull(indexOperations);
        indexOperations.delete();
    }
}
