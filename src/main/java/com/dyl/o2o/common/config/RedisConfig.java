package com.dyl.o2o.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author ：dyl
 * @date ：Created in 2019/12/25 22:40
 */
@Configuration //标明配置类，将此类实例化到spring容器中
@EnableCaching //开启基于注解的缓存，能够在服务类上标注@Cacheable，等价于xml中的<cache:annotation-driven/>，也不需要配置cache manage了
public class RedisConfig extends CachingConfigurerSupport {

    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String ,Object> template = new RedisTemplate<>();
        //配置连接工厂
        template.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper  objectMapper = new ObjectMapper();
        //指定要序列化的域(setter,getter等)和修饰符范围(设置可见度,ANY是都有，包括private和public)
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    }

}
