package cn.myst.web.distribute.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.scripting.support.ResourceScriptSource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 参考链接：<a href="https://www.cnblogs.com/niceyoo/p/13711149.html">https://www.cnblogs.com/niceyoo/p/13711149.html</a>
 * AutoCloseable: 自动关闭 - try () {}
 *
 * @author ziming.xing
 * Create Date：2022/4/21
 */
@Slf4j
public class RedisLock implements AutoCloseable {

    private final RedisTemplate redisTemplate;

    private final String key;

    /**
     * 不暴露出去，防止开发人员使用相同的值
     */
    private final String value;

    /**
     * 单位：秒
     */
    private final int expireTime;

    public RedisLock(RedisTemplate redisTemplate, String key, int expireTime) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.value = UUID.randomUUID().toString().replace("-", "");
        this.expireTime = expireTime;
    }

    /**
     * 获取分布式锁
     *
     * @return 是否锁成功
     */
    public boolean getLock() {
        RedisCallback<Boolean> redisCallback = connection -> {
            //设置NX
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            //设置过期时间
            Expiration expiration = Expiration.seconds(expireTime);
            //序列化key
            byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);
            //序列化value
            byte[] redisValue = redisTemplate.getValueSerializer().serialize(value);
            //执行Set NX操作
            if (redisKey == null || redisKey.length == 0 || redisValue == null || redisValue.length == 0) {
                return Boolean.FALSE;
            }

            return connection.set(redisKey, redisValue, expiration, setOption);
        };

        //获取分布式锁
        Object result = redisTemplate.execute(redisCallback);
        log.info("{} 锁定结果（true表示执行setNX，false表示未执行）：{}，开始处理业务", key, result);
        return Boolean.TRUE.equals(result);
    }


    /**
     * 释放分布式锁
     *
     * @return 是否释放成功
     */
    public boolean unLock() throws IOException {
        /*String script = "if redis.call('get', KEYS[1]) == ARGV[1]\n" +
                "    then\n" +
                "        return redis.call('del', KEYS[1])\n" +
                "    else\n" +
                "        return 0\n" +
                "end";*/
        ResourceScriptSource resourceScriptSource = new ResourceScriptSource(new ClassPathResource("redis-lock.lua"));
        String scriptAsString = resourceScriptSource.getScriptAsString();
        RedisScript<Boolean> redisScript = RedisScript.of(scriptAsString, Boolean.class);
        List<String> keys = Collections.singletonList(key);

        Boolean result = (Boolean) redisTemplate.execute(redisScript, keys, value);
        log.info("{} 解锁结果（true表示执行setNX，false表示未执行）：{}，开始处理业务", key, result);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public void close() throws Exception {
        boolean unLock = unLock();
        log.info("自动释放锁结果：{}", unLock);
    }

}
