package cn.myst.web.service.impl;

import cn.myst.web.es.pojo.Items;
import cn.myst.web.service.BaseService;
import cn.myst.web.service.ItemsESService;
import cn.myst.web.utils.PagedGridResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ziming.xing
 * Create Date：2021/10/30
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemsESServiceImpl implements ItemsESService {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final BaseService baseService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return null;
        }
        // 先构建基础查询条件对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 字段
        String itemNameField = "itemName";
        String sellCountsField = "sellCounts";
        String priceField = "price";

        // 字段搜索条件构建
        if (StringUtils.isNotBlank(keywords)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery(itemNameField, keywords));
        }

        // 排序
        FieldSortBuilder sortBuilder = null;
        if (StringUtils.equals(sort, "c")) {
            sortBuilder = SortBuilders.fieldSort(sellCountsField).order(SortOrder.DESC);
        } else if (StringUtils.equals(sort, "p")) {
            sortBuilder = SortBuilders.fieldSort(priceField).order(SortOrder.ASC);
        } else {
            // 注意：如果字段type = FieldType.Text，那么需要使用它的keyword去排序
            sortBuilder = SortBuilders.fieldSort(itemNameField + ".keyword").order(SortOrder.DESC);
        }

        // 分页条件
        Pageable pageable = PageRequest.of(page, pageSize);

        /*// 高亮前缀
        String preTag = EnumESHighlight.PRE_TAG.getValue();
        // 高亮后缀
        String postTag = EnumESHighlight.POST_TAG.getValue();*/

        // 组装条件
        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withHighlightFields(
                        // 使用默认高亮标签：em，由前端CSS样式去控制
                        new HighlightBuilder.Field(itemNameField)/*.preTags(preTag).postTags(postTag)*/
                )
                .withPageable(pageable)
                .withSort(sortBuilder)
                .build();

        // 查询结果
        SearchHits<Items> search = elasticsearchRestTemplate.search(query, Items.class);

        log.info("【综合化查询ES商品列表】- 查询结果：{}", search);
        // 得到查询结果返回的内容
        List<SearchHit<Items>> searchHits = search.getSearchHits();
        // 设置一个需要返回的实体类集合
        List<Items> list = new ArrayList<>();
        // 遍历返回的内容进行处理
        for (SearchHit<Items> searchHit : searchHits) {
            // 高亮的内容
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            // 将高亮的内容填充到content中
            Items content = searchHit.getContent();
            content.setItemName(
                    highlightFields.get(itemNameField) == null
                            ? content.getItemName()
                            : highlightFields.get(itemNameField).get(0)
            );
            // 放到实体类列表中
            list.add(content);
        }

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        PagedGridResult pagedGridResult = baseService.setterPagedGrid(page + 1, list);
        // 总记录数
        pagedGridResult.setRecords(search.getTotalHits());
        // 总页数
        pagedGridResult.setTotal((int) Math.ceil((double) pagedGridResult.getRecords() / pageSize));
        return pagedGridResult;
    }
}
