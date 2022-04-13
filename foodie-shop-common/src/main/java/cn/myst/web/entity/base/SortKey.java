package cn.myst.web.entity.base;

import cn.myst.web.constant.StringPool;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ziming.xing
 * Create Date：2021/6/20
 */
@Data
@Schema(title = "排序字段")
public class SortKey implements Serializable {
    /**
     * The ID of the attribute to sort by.
     */
    @Schema(title = "排序字段", description = "要排序的属性的名称")
    private String field;


    /**
     * The sort order. Ascending order, by default.
     */
    @Schema(title = "排序顺序", description = "排序顺序，默认为升序")
    private String sort;

    /**
     * 排序时字段为null时指定的缺省最小值
     */
    private Object nullMinValue;

    /**
     * 排序时字段为null时指定的缺省最大值
     */
    private Object nullMaxValue;

    /**
     * access 要素更改getter或setter或object字段定义的逻辑属性的可见性。
     * access的值可以是以下之一:
     * Access.WRITE_ONLY：逻辑属性的可见性仅在我们将JSON数据设置为Java对象时，即在反序列化时才可用。
     * Access.READ_ONLY：逻辑属性的可见性仅在我们从Java对象获取JSON数据时才可用，即在序列化时。
     * Access.READ_WRITE：逻辑属性的可见性在序列化和反序列化时都可用。
     * Access.AUTO：将自动确定逻辑属性的可见性，这是access元素的默认值。
     */
    @Schema(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean getSortBoolean() {
        return Objects.equals(this.getSort(), "ASC");
    }

    public static SortKey of(String key, String sort) {
        SortKey sk = new SortKey();
        sk.setField(key);
        sk.setSort(sort);
        return sk;
    }

    @Override
    public String toString() {
        return sort + " " + sort;
    }

    public boolean isAsc() {
        return StringUtils.isBlank(sort) || sort.equalsIgnoreCase(StringPool.ASC);
    }

    private static final long serialVersionUID = 9193738549825989543L;
}
