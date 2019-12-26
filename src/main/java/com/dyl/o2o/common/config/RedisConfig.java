package com.dyl.o2o.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ：dyl
 * @date ：Created in 2019/12/25 22:40
 */
@Configuration //标明配置类，将此类实例化到spring容器中
@EnableCaching //开启基于注解的缓存，能够在服务类上标注@Cacheable，等价于xml中的<cache:annotation-driven/>，也不需要配置cache manage了
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * redisTemplate相关配置
     * @param factory
     * @return
     */
    @Bean //将该方法交给spring管理；spring调用产生bean对象，产生Bean对象的方法spring只会调用一次，产生的Bean会放到自己的IOC容器中
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String ,Object> template = new RedisTemplate<>();
        //配置连接工厂
        template.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper  objectMapper = new ObjectMapper();
        //指定要序列化的域(setter,getter等)和修饰符范围(设置可见度,ANY是都有，包括private和public)
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //指定序列化输入的类型，必须是非final的，若为final则会跑出异常
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //设置要序列化
        jacksonSerializer.setObjectMapper(objectMapper);

        //值采用json序列化
        template.setValueSerializer(jacksonSerializer);
        //使用stringRedisSerializer来序列化和反序列化redis的key
        template.setKeySerializer(new StringRedisSerializer());

        //设置hash的key和value的序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSerializer);
        template.afterPropertiesSet();

        return template;
    }

    /**
     * 对hash类型的数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型的数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForValue();
    }

    /**
     * 对list链表类型的数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate){
        return redisTemplate.opsForZSet();
    }
}
