package cn.myst.web;

import cn.myst.web.es.pojo.Stu;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 不建议使用 ElasticsearchRestTemplate 对索引进行管理（创建索引，更新映射，删除索引）
 * 索引就像数据库或数据库中的表，我们平时是不会通过java代码频繁的去创建/修改数据库或者数据表
 * 我们只会针对数据做CRUD的操作
 * 在ES中也是同理，尽量使用 ElasticsearchRestTemplate 对文档数据做CRUD的操作
 * 1、属性（FiledType）类型不灵活
 * 2、主分片与副分片数无法设置
 * <p>
 * 参考：https://blog.csdn.net/wo18237095579/article/details/117996071
 *
 * @author ziming.xing
 * Create Date：2021/9/16
 */
@Ignore
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SearchApplication.class)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
class ESTest {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    //    —————————— 索引操作 ——————————

    @Test
    void createIndexStu() {
        Stu stu = new Stu();
        stu.setStuId(1005L);
        stu.setName("spider man");
        stu.setAge(18);
        stu.setMoney(18.8F);
        stu.setSign("i am spider man");
        stu.setDescription("I wish am spider man");

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

    //    —————————— 文档操作 ——————————

    @Test
    void updateStuDocument() {
        String id = "1002";
        // 索引
        String indexName = "stu";
        IndexCoordinates indexCoordinates = IndexCoordinates.of(indexName);

        // 更新信息
        Document document = Document.create();
        document.put("age", "20");

        // 构建更新对象
        UpdateQuery updateQuery = UpdateQuery.builder(id)
                .withDocument(document)
                .build();
        assertNotNull(updateQuery);

        // 增量更新
        elasticsearchRestTemplate.update(updateQuery, indexCoordinates);
    }

    @Test
    void getIndexStuDocument() {
        // 根据id查询文档数据
        Stu stu = elasticsearchRestTemplate.get("1002", Stu.class);
        assertNotNull(stu);
        log.info(stu.toString());
    }

    //    —————————— 搜索操作 ——————————

    @Test
    void searchStuDocument() {
        // 先构建基础查询条件对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 描述
        String description = "save man";

        // 描述字段搜索条件构建
        if (StringUtils.isNotBlank(description)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("description", description));
        }

        // 排序
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("age").order(SortOrder.DESC);

        // 分页条件
        Pageable pageable = PageRequest.of(0, 2);

        // 组装条件
        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
//                .withQuery(QueryBuilders.matchQuery("description", "save man"))
                .withPageable(pageable)
                .withSort(sortBuilder)
                .build();

        // 查询结果
        SearchHits<Stu> searchHits = elasticsearchRestTemplate.search(query, Stu.class);
        assertNotNull(searchHits);
        log.info("【综合化查询学生列表】- 查询结果：{}", searchHits);

        List<Stu> stuList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        log.info("【综合化查询学生列表】- 查询结果列表：{}", stuList);
    }

    //    —————————— 高亮操作 ——————————

    @Test
    void highlightStuDocument() {
        // 先构建基础查询条件对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 描述
        String description = "am man";

        // 描述字段搜索条件构建
        if (StringUtils.isNotBlank(description)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("description", description));
        }

        // 排序
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("age").order(SortOrder.DESC);

        // 分页条件
        Pageable pageable = PageRequest.of(0, 2);

        // 高亮前缀
        String preTag = "<font color='red'>";
        // 高亮后缀
        String postTag = "</font>";

        // 组装条件
        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
//                .withQuery(QueryBuilders.matchQuery("description", "save man"))
                .withHighlightFields(
                        new HighlightBuilder.Field("description").preTags(preTag).postTags(postTag)
                )
                .withPageable(pageable)
                .withSort(sortBuilder)
                .build();

        // 查询结果
        SearchHits<Stu> search = elasticsearchRestTemplate.search(query, Stu.class);
        assertNotNull(search);
        log.info("【综合化查询学生列表】- 查询结果：{}", search);
        // 得到查询结果返回的内容
        List<SearchHit<Stu>> searchHits = search.getSearchHits();
        // 设置一个需要返回的实体类集合
        List<Stu> stuList = new ArrayList<>();
        // 遍历返回的内容进行处理
        for (SearchHit<Stu> searchHit : searchHits) {
            // 高亮的内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            // 将高亮的内容填充到content中
            Stu content = searchHit.getContent();
            content.setDescription(
                    highlightFields.get("description") == null
                            ? content.getDescription()
                            : highlightFields.get("description").get(0)
            );
            // 放到实体类列表中
            stuList.add(content);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("stuList", stuList);
        resultMap.put("totalCount", search.getTotalHits());

        log.info("【综合化查询学生列表】- 查询结果列表：{}", stuList);
        log.info("【综合化查询学生列表】- 查询总数：{}", search.getTotalHits());
        log.info("【综合化查询学生列表】- 查询结果：{}", resultMap);
    }
}
