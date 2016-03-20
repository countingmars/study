package io.github.countingmars;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Mars on 3/20/16.
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultSerializer(serializer());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    private GenericJackson2JsonRedisSerializer serializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    @Bean
    public JedisConnectionFactory connectionFactory() {
        try {
            URI uri = new URI("redis://@localhost:6379");
            JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
            connectionFactory.setUsePool(true);
            connectionFactory.setHostName(uri.getHost());
            connectionFactory.setPort(uri.getPort());
            return connectionFactory;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("@cache:");
            sb.append(target.getClass().getName());
            sb.append("::").append(method.getName());
            for (Object obj : params) {
                sb.append(":").append(obj.toString());
            }
            return sb.toString();
        };
    }

}
