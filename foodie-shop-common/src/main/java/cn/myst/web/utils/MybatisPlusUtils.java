package cn.myst.web.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ziming.xing
 * Create Date：2021/5/19
 */
public class MybatisPlusUtils {
    private MybatisPlusUtils() {
    }

    @SuppressWarnings("rawtypes")
    private static final Page EMPTY_PAGE = new Page<>();

    public static <T> LambdaQueryWrapper<T> lambdaQuery() {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        return wrapper.lambda();
    }

    public static <T> UpdateWrapper<T> updatedWrapper() {
        return new UpdateWrapper<>();
    }

    public static <T> LambdaUpdateWrapper<T> lambdaUpdate() {
        UpdateWrapper<T> wrapper = new UpdateWrapper<>();
        return wrapper.lambda();
    }

    /**
     * 对MybatisPlus分页查询结果进行转换
     *
     * @param page      原始分页查询结果
     * @param converter 单个数据记录转换函数
     * @param <T>       原始类型
     * @param <R>       转换后类型
     * @return 转换结果
     */
    public static <T, R> Page<R> convertPage(Page<T> page, Function<T, R> converter) {
        if (page == null) {
            return null;
        }

        Page<R> p = new Page<>();
        BeanUtils.copyProperties(page, p);
        List<T> records = page.getRecords();
        if (records.isEmpty()) {
            return p;
        }

        p.setRecords(records.stream().map(converter).collect(Collectors.toList()));
        return p;
    }

    /**
     * 对MybatisPlus分页查询结果进行转换
     *
     * @param page 原始分页查询结果
     * @param data 装换后数据列表
     * @param <T>  原始类型
     * @param <R>  转换后类型
     * @return 转换结果
     */
    public static <T, R> Page<R> convertPage(Page<T> page, List<R> data) {
        if (page == null) {
            return null;
        }

        Page<R> result = new Page<>();
        BeanUtils.copyProperties(page, result);
        result.setRecords(data);

        return result;
    }

    public static <R> Page<R> page(int page, int pageSize, int total, List<R> data) {

        Page<R> result = new Page<>();
        result.setTotal(total);
        result.setCurrent(page);
        result.setSize(pageSize);
        result.setRecords(data);

        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T> Page<T> emptyPage() {
        return EMPTY_PAGE;
    }

}
