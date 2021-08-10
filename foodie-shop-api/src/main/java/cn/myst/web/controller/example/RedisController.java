package cn.myst.web.controller.example;

import cn.myst.web.utils.RedisOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ziming.xing
 * Create Date：2021/8/9
 */
//@ApiIgnore
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


}
