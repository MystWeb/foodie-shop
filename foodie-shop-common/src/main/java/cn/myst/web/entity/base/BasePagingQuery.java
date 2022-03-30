package cn.myst.web.entity.base;

import cn.myst.web.constant.StringPool;
import cn.myst.web.utils.SortKeyUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ziming.xing
 * Create Date：2021/6/19
 */
@Data
@Schema(title = "基础分页查询")
public class BasePagingQuery implements Serializable {
    @Schema(name = "第几页", type = "int", example = "1", required = true)
    private transient Integer page = 1;

    @Schema(name = "每页显示条数", type = "int", example = "10", required = true)
    private transient Integer pageSize = 10;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(name = "关键字")
    private transient String keywords;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(name = "排序字段集")
    private transient List<SortKey> sortKeys;

    /**
     * 构建好的排序字符串
     */
    @JsonIgnore
    @Schema(hidden = true)
    private transient String sortString;

    /**
     * 分页查询请求转换未MybatisPlus的分页参数
     *
     * @param <T> 数据类型
     * @return MybatisPlus的分页参数
     */
    public <T> Page<T> toMybatisPlusPage() {
        return new Page<>(page, pageSize);
    }

    /**
     * 解决前端排序的字段名跟数据库列名不匹配的问题
     *
     * @param frontendColumn 前端排序列名
     * @param dbTableColumn  数据库表中的列名，如果SQL中使用了别名，该字段也要包含别名，例如 t1.equipment_name
     * @return 是否匹配到
     */
    public boolean resolve(String frontendColumn, String dbTableColumn) {
        return resolve(frontendColumn, dbTableColumn, null, null);
    }

    public boolean resolve(String frontendColumn, String dbTableColumn, boolean nullValueAlwaysLast) {
        if (CollectionUtils.isEmpty(sortKeys)) {
            return false;
        }

        for (SortKey sortKey : sortKeys) {
            if (Objects.equals(sortKey.getField(), frontendColumn)) {
                sortKey.setField(dbTableColumn);
                if (nullValueAlwaysLast) {
                    sortKey.setNullMinValue(Integer.MIN_VALUE);
                    sortKey.setNullMaxValue(Integer.MAX_VALUE);
                }

                return true;
            }
        }

        return false;
    }

    public boolean resolve(String frontendColumn, String dbTableColumn, Object nullMinValue, Object nullMaxValue) {
        if (CollectionUtils.isEmpty(sortKeys)) {
            return false;
        }

        for (SortKey sortKey : sortKeys) {
            if (Objects.equals(sortKey.getField(), frontendColumn)) {
                sortKey.setField(dbTableColumn);
                sortKey.setNullMinValue(nullMinValue);
                sortKey.setNullMaxValue(nullMaxValue);
                return true;
            }
        }

        return false;
    }

    /**
     * 根据带有SortColumn注解的类，将前端传入的排序字段转为数据库排序字段
     *
     * @param clz 带有SortColumn注解的类
     *            据库排序字段集合
     */
    public void resolveAll(Class<?> clz) {
        // 构建sortString
        this.setSortKeys(SortKeyUtils.resolveAll(this.getSortKeys(), clz));
    }

    public String toSortString(String defaultSort) {
        if (!CollectionUtils.isEmpty(sortKeys)) {
            return "order by " + sortKeys.stream()
                    .map(sk -> {
                        String key = sk.getField();
                        if (sk.getNullMaxValue() != null && sk.isAsc()) {
                            key = "IFNULL(" + sk.getField() + ", " + sk.getNullMaxValue() + ")";
                        }

                        if (sk.getNullMinValue() != null && !sk.isAsc()) {
                            key = "IFNULL(" + sk.getField() + ", " + sk.getNullMinValue() + ")";
                        }
                        return key + " " + sk.getSort();
                    })
                    .collect(Collectors.joining(", "));
        }

        return defaultSort;
    }

    public String toSortString() {
        return toSortString("");
    }

    public void setDefaultSort(String sortKey, boolean asc) {
        if (sortKeys == null) {
            sortKeys = Lists.newArrayList();
        }
        if (sortKeys.isEmpty()) {
            sortKeys.add(SortKey.of(sortKey, asc ? StringPool.ASC : StringPool.DESC));
        }
    }

    private static final long serialVersionUID = 4194702170257027437L;
}
