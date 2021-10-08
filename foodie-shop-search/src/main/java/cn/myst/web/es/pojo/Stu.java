package cn.myst.web.es.pojo;

import lombok.Builder;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;

/**
 * 学生对象
 * <p>
 * 注解：@Document用来声明Java对象与ElasticSearch索引的关系
 * indexName 索引名称
 * type      索引类型（ES 6.x）
 * shards    主分区数量，默认5
 * replicas  副本分区数量，默认1
 * createIndex 索引不存在时，是否自动创建索引，默认true
 * <p>
 * 参考：https://blog.csdn.net/wo18237095579/article/details/117996071
 *
 * @author ziming.xing
 * Create Date：2021/9/17
 */
@Builder
@ToString
// 创建索引使用，默认项目启动时自动创建索引
@Document(indexName = "stu", createIndex = false)
// 设置分片和副本
@Setting(shards = 3, replicas = 1)
public class Stu implements Serializable {

    @Id
    private Long stuId;

    @Field(store = true)
    private String name;

    @Field(store = true)
    private Integer age;

    @Field(store = true)
    private Float money;

    @Field(store = true, type = FieldType.Keyword)
    private String sign;

    @Field(store = true)
    private String description;

    private static final long serialVersionUID = 8304979729719591574L;
}
