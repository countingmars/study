package io.github.countingmars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Mars on 3/20/16.
 */
@Service
public class RedisMessageService {
    @Autowired
    RedisTemplate redisTemplate;

    public Message get() {
        return (Message) redisTemplate.boundValueOps("message").get();

    }
    public void set(Message message) {
        redisTemplate.boundValueOps("message").set(message);
    }

    @Cacheable(value = "message")
    public Message cache(int p) {
        return get();
    }
}
