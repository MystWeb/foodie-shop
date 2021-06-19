package cn.myst.web.utils;

import cn.myst.web.annotation.SortColumn;
import cn.myst.web.entity.base.BasePagingQuery;
import cn.myst.web.entity.base.SortKey;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author ziming.xing
 * Create Date：2021/6/20
 */
public final class SortKeyUtils {
    private static final Map<Class<?>, Map<String, String>> CACHE = new HashMap<>(8);
    private static final PropertyNamingStrategy.SnakeCaseStrategy STRATEGY = new PropertyNamingStrategy.SnakeCaseStrategy();

    private SortKeyUtils() {
    }

    /**
     * 根据带有SortColumn注解的类，将前端传入的排序字段转为数据库排序字段
     *
     * @param page 前端排序字段集合
     * @param clz  带有SortColumn注解的类
     * @return 据库排序字段集合
     */
    public static BasePagingQuery resolve(BasePagingQuery page, Class<?> clz) {
        List<SortKey> sortKeys = resolve(page.getSortKeys(), clz);
        page.setSortKeys(sortKeys);
        return page;
    }

    /**
     * 根据带有SortColumn注解的类，将前端传入的排序字段转为数据库排序字段
     *
     * @param sortKeys 前端排序字段集合
     * @param clz      带有SortColumn注解的类
     * @return 据库排序字段集合
     */
    public static List<SortKey> resolve(Collection<SortKey> sortKeys, Class<?> clz) {
        Objects.requireNonNull(clz);

        if (CollectionUtils.isEmpty(sortKeys)) {
            return Collections.emptyList();
        }

        // 根据类获取数据库列名映射配置项
        Map<String, String> columnMap = findColumnMap(clz);
        if (CollectionUtils.isEmpty(columnMap)) {
            return Collections.emptyList();
        }

        // 遍历前端传入的排序字段，根据配置转为数据库列名
        List<SortKey> sortColumns = new ArrayList<>(sortKeys.size());
        for (SortKey key : sortKeys) {
            String sortName = key.getField();
            String column = columnMap.get(sortName);

            // 没有找到映射的column，忽略该排序字段
            if (column == null) {
                continue;
            }
            SortKey sortKey = new SortKey();
            sortKey.setField(column);
            sortKey.setSort(key.getSort());

            sortColumns.add(sortKey);
        }
        return sortColumns;
    }

    private static Map<String, String> findColumnMap(Class<?> clz) {
        Map<String, String> cacheItem = CACHE.get(clz);
        if (cacheItem != null) {
            return cacheItem;
        }

        // 加锁防止并发初始化缓存
        synchronized (clz.getName()) {
            // 二次校验
            cacheItem = CACHE.get(clz);
            if (cacheItem != null) {
                return cacheItem;
            }

            cacheItem = new HashMap<>(8);

            for (Field field : clz.getDeclaredFields()) {
                // 通过注解获取要映射的数据库列名
                SortColumn ann = field.getDeclaredAnnotation(SortColumn.class);
                if (ann == null) {
                    continue;
                }

                String value = getColumn(ann.value(), ann.tableAlias(), field);

                cacheItem.put(toCacheKey(field), value);
            }

            // 必须最后一步放入缓存，否则提前放入会被其他线程提前拿到不完整的数据
            CACHE.put(clz, cacheItem);
            return cacheItem;
        }
    }

    /**
     * 根据带有SortColumn注解的类，将前端传入的排序字段转为数据库排序字段
     *
     * @param sortKeys 前端排序字段集合
     * @param clz      带有SortColumn注解的类
     * @return 据库排序字段集合
     */
    public static List<SortKey> resolveAll(Collection<SortKey> sortKeys, Class<?> clz) {
        Objects.requireNonNull(clz);

        if (CollectionUtils.isEmpty(sortKeys)) {
            return Collections.emptyList();
        }

        // 根据类获取数据库列名映射配置项
        Map<String, String> columnMap = findAllColumnMap(clz);
        if (CollectionUtils.isEmpty(columnMap)) {
            return Collections.emptyList();
        }

        // 遍历前端传入的排序字段，根据配置转为数据库列名
        List<SortKey> sortColumns = new ArrayList<>(sortKeys.size());
        for (SortKey key : sortKeys) {
            String sortName = key.getField();
            String column = columnMap.get(sortName);

            // 没有找到映射的column，忽略该排序字段
            if (column == null) {
                continue;
            }
            SortKey sortKey = new SortKey();
            sortKey.setField(column);
            sortKey.setSort(key.getSort());

            sortColumns.add(sortKey);
        }
        return sortColumns;
    }

    private static Map<String, String> findAllColumnMap(Class<?> clz) {
        Map<String, String> cacheItem = CACHE.get(clz);
        if (cacheItem != null) {
            return cacheItem;
        }

        // 加锁防止并发初始化缓存
        synchronized (clz.getName()) {
            // 二次校验
            cacheItem = CACHE.get(clz);
            if (cacheItem != null) {
                return cacheItem;
            }

            cacheItem = new HashMap<>(8);

            // 控制层数
            int num = 0;
            // 向上查找一直查找到Object类
            do {
                for (Field field : clz.getDeclaredFields()) {
                    // 通过注解获取要映射的数据库列名
                    SortColumn ann = field.getDeclaredAnnotation(SortColumn.class);
                    if (ann == null) {
                        continue;
                    }

                    String value = getColumn(ann.value(), ann.tableAlias(), field);

                    cacheItem.put(toCacheKey(field), value);
                }
                clz = clz.getSuperclass();
                num++;
            } while (!clz.getName().equals("java.lang.Object") || num >= 10);

            // 必须最后一步放入缓存，否则提前放入会被其他线程提前拿到不完整的数据
            CACHE.put(clz, cacheItem);
            return cacheItem;
        }
    }

    private static String getColumn(String column, String tableAlias, Field field) {
        // 如果注解没有指定具体列名，则使用类中的field名称
        String value = column.isEmpty() ? STRATEGY.translate(field.getName()) : column;

        if (!tableAlias.isEmpty()) {
            value = tableAlias + "." + value;
        }
        return value;
    }

    /**
     * 历史原因，前端传给后端的sort key字段都已经进行了驼峰转下划线处理，
     * 所有不能直接用field.name，需要转为下划线方式
     *
     * @param field 成员字段
     * @return key值得
     */
    private static String toCacheKey(Field field) {
        String fieldName = field.getName();
        return STRATEGY.translate(fieldName);
    }
}