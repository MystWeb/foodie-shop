package cn.myst.web.controller.example;

import cn.myst.web.utils.RedisOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;

/**
 * @author ziming.xing
 * Create Date：2021/8/9
 */
@ApiIgnore
@RestController
@RequestMapping("redis")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RedisController {
    private final RedisTemplate<String, String> redisTemplate;

    private final RedisOperator redisOperator;

    @GetMapping("set")
    public Object set(String key, String value) {
//        redisTemplate.opsForValue().set(key, value);
        redisOperator.set(key, value);
        return "OK";
    }

    @GetMapping("get")
    public String get(String key) {
//        return redisTemplate.opsForValue().get(key);
        return redisOperator.get(key);
    }

    @GetMapping("delete")
    public Object delete(String key) {
//        redisTemplate.delete(key);
        redisOperator.del(key);
        return "OK";
    }

    /**
     * 大量key查询—multiGet
     */
    @GetMapping("getALot")
    public List<String> getALot(String... keys) {
//        redisTemplate.opsForValue().multiGet(Arrays.asList(keys));
        return redisOperator.mget(Arrays.asList(keys));
    }

    /**
     * 批量key查询—pipeline
     */
    @GetMapping("batchGet")
    public List<Object> batchGet(List<String> keys) {
        // nginx -> keepalive
        // 用户每次请求都会有一个打开/关闭的消耗，为了减少该损耗为最低，
        // 在一段时间内，用户频繁请求接口，Keepalive以一个长连接的形式保证用户进行一个源源不断的访问，从而提高用户体验、吞吐量

        // redis -> pipeline
        // 管道（pipeline）包含了多次不同的查询（key），
        // 我们把不同的key放入管道，那么只会在建立管道的时候，有一次损耗，最终管道会关闭，比循环查询单个key，性能、吞吐量则更高
        /*return redisTemplate.executePipelined((RedisCallback<String>) redisConnection -> {
            StringRedisConnection src = (StringRedisConnection) redisConnection;
            for (String key : keys) {
                src.get(key);
            }
            return null;
        });*/

        return redisOperator.batchGet(keys);
    }

}
