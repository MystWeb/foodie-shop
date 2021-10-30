package cn.myst.web.es.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2021/10/30
 */
@Data
@Document(indexName = "foodie-items-ik", createIndex = false)
public class Items implements Serializable {

    @Id
    @Field(store = true, type = FieldType.Text, index = false)
    private String itemId;

    @Field(store = true, type = FieldType.Text)
    private String itemName;

    @Field(store = true, type = FieldType.Text, index = false)
    private String imgUrl;

    @Field(store = true, type = FieldType.Integer)
    private Integer price;

    @Field(store = true, type = FieldType.Integer)
    private Integer sellCounts;

    private static final long serialVersionUID = -5056447998084461608L;
}
