package cn.myst.web.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ziming.xing
 * Create Date：2021/7/21
 */
public class CommonUtils {
    private CommonUtils() {
    }

    public static <T, R> List<R> convert(List<T> records, Function<T, R> mapper) {
        Objects.requireNonNull(mapper);

        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }

        return records.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 收集id列表，拼接成字符串
     * 如果列表为空，会返回空字符串，而不是null，调用的时候注意判断
     */
    public static <T> String gatherIds(Collection<T> list, Function<T, Object> mapper) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return String.join(",", list.parallelStream().map(mapper).filter(Objects::nonNull).map(String::valueOf).collect(Collectors.toSet()));
    }

    public static <T, R> Set<R> getFields(Collection<T> entities, Function<T, R> mapper) {
        if (CollectionUtils.isEmpty(entities)) {
            return new HashSet<>();
        }
        return entities.stream().map(mapper).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * toMap的包装
     */
    public static <T, K> Map<K, T> toMap(List<T> list, Function<? super T, ? extends K> keyMapper) {
        return toMap(list, keyMapper, Function.identity());
    }

    /**
     * toMap
     */
    public static <T, K, V> Map<K, V> toMap(List<T> list, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return Optional.ofNullable(list).orElse(new ArrayList<>()).stream()
                .filter(item -> keyMapper.apply(item) != null)
                .collect(Collectors.toMap(keyMapper, valueMapper, (v1, v2) -> v1, LinkedHashMap::new));
    }

    /**
     * 集合转为map，对比toMap，该函数允许经过valueMapper转换后的值为null
     */
    public static <T, K, V> Map<K, V> toMapExt(Collection<T> list, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return Optional.ofNullable(list).orElse(new ArrayList<>()).stream()
                .filter(item -> keyMapper.apply(item) != null)
                .collect(HashMap::new, (map, item) -> map.put(keyMapper.apply(item), valueMapper.apply(item)), HashMap::putAll);
    }

    /**
     * groupingBy的包装
     */
    public static <T, K> Map<K, List<T>> group2Map(Collection<T> list, Function<? super T, ? extends K> keyMapper) {
        return group2Map(Optional.ofNullable(list).orElse(new ArrayList<>()).stream(), keyMapper);
    }

    /**
     * groupingBy的包装，传入流，用于一些能够直接获取流的场景
     */
    public static <T, K> Map<K, List<T>> group2Map(Stream<T> stream, Function<? super T, ? extends K> keyMapper) {
        return stream.filter(Objects::nonNull)
                .filter(item -> keyMapper.apply(item) != null)
                .collect(Collectors.groupingBy(keyMapper));
    }

    /**
     * 分组统计
     * 先按照key值排序，如果需要根据名称排序，对结果list进行排序
     */
    public static <T, K> Map<K, Long> groupCounting(Collection<T> list, Function<? super T, ? extends K> keyMapper) {
        return Optional.ofNullable(list).orElse(new ArrayList<>()).stream()
                .filter(item -> keyMapper.apply(item) != null)
                .collect(Collectors.groupingBy(keyMapper, TreeMap::new, Collectors.counting()));
    }

    /**
     * list是否相同比较方法
     */
    public static boolean isListEqual(List<?> l0, List<?> l1) {
        if (l0 == l1) {
            return true;
        }
        if (l0 == null || l1 == null) {
            return false;
        }
        if (l0.size() != l1.size()) {
            return false;
        }
        for (Object o : l0) {
            if (!l1.contains(o)) {
                return false;
            }
        }
        for (Object o : l1) {
            if (!l0.contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 增加引号
     * 用于前端传递如A, B, C样式的参数，后端加工成'A', 'B', 'C'式样
     */
    public static String addQuotationMark(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        // 如果传过来的值本身已经带有引号则直接返回
        if (StringUtils.startsWithIgnoreCase(str, "'") && StringUtils.endsWithIgnoreCase(str, "'")) {
            return str;
        }
        String[] arr = str.split(",");
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append("'");
            sb.append(s.trim());
            sb.append("',");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * 拷贝列表
     *
     * @param sources 数据源类
     * @param target  目标类
     * @param <S>     数据源类
     * @param <T>     目标类
     * @return 目标类列表
     * <p>
     * 使用场景：Entity、Bo、Vo层数据的复制，因为BeanUtils.copyProperties只能给目标对象的属性赋值，却不能在List集合下循环赋值，因此添加该方法
     * 如：List<AdminEntity> 赋值到 List<AdminVo> ，List<AdminVo>中的 AdminVo 属性都会被赋予到值
     * S: 数据源类 ，T: 目标类::new(eg: AdminVo::new)
     */
    @NonNull
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }

    /**
     * @param sources 数据源类
     * @param target  目标类
     * @param <S>     数据源类
     * @param <T>     目标类
     * @return 目标类列表
     * <p>
     * 使用场景：Entity、Bo、Vo层数据的复制，因为BeanUtils.copyProperties只能给目标对象的属性赋值，却不能在List集合下循环赋值，因此添加该方法
     * 如：List<AdminEntity> 赋值到 List<AdminVo> ，List<AdminVo>中的 AdminVo 属性都会被赋予到值
     * S: 数据源类 ，T: 目标类::new(eg: AdminVo::new)
     */
    @NonNull
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BeansUtilsCallBack<S, T> callBack) {
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            BeanUtils.copyProperties(source, t);
            list.add(t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
        }
        return list;
    }

    /**
     * 求sum
     */
    public static <T> Double sum(Collection<T> list, ToDoubleFunction<T> mapper) {
        return Optional.ofNullable(list).orElse(new ArrayList<>()).stream().mapToDouble(mapper).sum();
    }

    /**
     * 使用java8的lambda表达式注解：BeansUtilsCallBack接口
     *
     * @author ziming.xing
     * Create Date：2020/7/3
     */
    @FunctionalInterface
    public interface BeansUtilsCallBack<S, T> {
        void callBack(S t, T s);
    }

    /**
     * subList方式分页
     */
    public static <T> List<T> page(List<T> list, Integer pageNum, Integer pageSize) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        int count = list.size();
        int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = pageNum.equals(pageCount) ? count : fromIndex + pageSize;
        return list.subList(fromIndex, toIndex);
    }
}
