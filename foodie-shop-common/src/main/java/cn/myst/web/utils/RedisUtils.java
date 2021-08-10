package cn.myst.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author ziming.xing
 * Create Time：2020/5/25
 */
@Slf4j
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    @Resource
    private RedisTemplate<String, String> stringStringRedisTemplate;

    private static RedisTemplate<String, Object> redisTemplate;
    private static RedisTemplate<String, String> stringRedisTemplate;

    @PostConstruct
    public void init() {
        redisTemplate = stringObjectRedisTemplate;
        stringRedisTemplate = stringStringRedisTemplate;
    }

//    ----------------------------- String 操作 ------------------------

    /**
     * 获取普通对象
     *
     * @param key 键
     * @return 对象
     */
    public static String getStr(final String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 获取普通对象
     *
     * @param key 键
     * @return 对象
     */
    public static Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量获取普通对象
     *
     * @param keys 键
     * @return 对象
     */
    public static List<Object> mGet(final String... keys) {
        if (keys == null || keys.length <= 0) {
            return null;
        }
        ArrayList keyList = new ArrayList();
        for (String key : keys) {
            keyList.add(key);
        }
        return mGet(keyList);
    }


    /**
     * 批量获取普通对象
     *
     * @param keys 键
     * @return 对象
     */
    public static List<Object> mGet(final Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 存入普通对象
     *
     * @param key   Redis键
     * @param value 值
     */
    public static void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value, 1, TimeUnit.SECONDS);
    }


    /**
     * 存入普通对象
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效期，单位秒
     */
    public static void setExpire(final String key, final Object value, final long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 判断keys其中任意键中是否已存在
     *
     * @param keys 键
     * @return 对象
     */
    public static Boolean existAny(final String... keys) {
        if (keys == null || keys.length == 0) {
            return Boolean.FALSE;
        }
        for (String key : keys) {
            if (redisTemplate.opsForValue().get(key) != null) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(final String key, final long timeout, final TimeUnit unit) {

        Boolean ret = redisTemplate.expire(key, timeout, unit);
        return ret != null && ret;
    }

    /**
     * 删除单个key
     *
     * @param key 键
     * @return true=删除成功；false=删除失败
     */
    public static boolean del(final String key) {

        Boolean ret = redisTemplate.delete(key);
        return ret != null && ret;
    }

    /**
     * 删除多个key
     *
     * @param keys 键
     * @return 成功删除的个数
     */
    public static long del(final String... keys) {
        if (keys == null || keys.length <= 0) {
            return 0;
        }
        ArrayList keyList = new ArrayList();
        for (String key : keys) {
            keyList.add(key);
        }
        return del(keyList);
    }

    /**
     * 删除多个key
     *
     * @param keys 键集合
     * @return 成功删除的个数
     */
    public static long del(final Collection<String> keys) {
        Long ret = redisTemplate.delete(keys);
        return ret == null ? 0 : ret;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

//    ----------------------------- Hash 操作 ------------------------

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public static Object hGet(final String key, final String hKey) {
        return redisTemplate.opsForHash().get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public static List<Object> hMultiGet(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }


    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public static void hPut(final String key, final String hKey, final Object value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 往Hash中存入多个数据
     *
     * @param key    Redis键
     * @param values Hash键值对
     */
    public static void hPutAll(final String key, final Map<String, Object> values) {
        redisTemplate.opsForHash().putAll(key, values);
    }

//    ----------------------------- Set 操作 ------------------------

    /**
     * 获取Set中是否存在key,value的数据
     *
     * @param key Redis键
     * @return 是否存在
     */
    public static Set<Object> sGet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取Set中是否存在key,value的数据
     *
     * @param key Redis键
     * @return 是否存在
     */
    public static Set<String> sGetStr(final String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    /**
     * 获取Set中key的数据
     *
     * @param key Redis键
     * @return Set中key的数据
     */
    public static Boolean sExist(final String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取Set中key的数据
     *
     * @param key Redis键
     * @return Set中key的数据
     */
    public static Boolean sExistStr(final String key, String value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 往Set中存入数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 存入的个数
     */
    public static long sSet(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往Set中存入数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 存入的个数
     */
    public static long sSet(final String key, final Collection<Object> values) {
        Object[] objects = values.toArray();
        Long count = redisTemplate.opsForSet().add(key, objects);
        return count == null ? 0 : count;
    }

    /**
     * 往Set中存入数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 存入的个数
     */
    public static long sSetStr(final String key, final String... values) {
        Long count = stringRedisTemplate.opsForSet().add(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往Set中存入数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 存入的个数
     */
    public static long sSetStr(final String key, final Collection<String> values) {
        String[] strValues = values.toArray(new String[]{});
        Long count = stringRedisTemplate.opsForSet().add(key, strValues);
        return count == null ? 0 : count;
    }

    /**
     * 删除Set中的数据
     *
     * @param key    Redis键
     * @param values 值
     * @return 移除的个数
     */
    public static long sDel(final String key, final Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

//    ----------------------------- List 操作 ------------------------

    /**
     * 从List中获取begin到end之间的元素
     *
     * @param key   Redis键
     * @param start 开始位置
     * @param end   结束位置（start=0，end=-1表示获取全部元素）
     * @return List对象
     */
    public static List<Object> lGet(final String key, final int start, final int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 往List中存入数据
     *
     * @param key   Redis键
     * @param value 数据
     * @return 存入的个数
     */
    public static long lPush(final String key, final Object value) {
        Long count = redisTemplate.opsForList().rightPush(key, value);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key    Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public static long lPushAll(final String key, final Collection<Object> values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }

    /**
     * 往List中存入多个数据
     *
     * @param key    Redis键
     * @param values 多个数据
     * @return 存入的个数
     */
    public static long lPushAll(final String key, final Object... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        return count == null ? 0 : count;
    }
}
