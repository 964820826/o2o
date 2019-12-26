package com.dyl.o2o.common.util;

import com.mysql.cj.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

/**
 * Redis的工具类
 * @author ：dyl
 * @date ：Created in 2019/12/26 12:40
 */
@Component //使该类在自动装配时能被扫描到，相当于xml中的<bean id="" class""/>，即声明一个bean
@Slf4j
public class RedisUtil {

//    @Autowired  todo 私有变量为何需要自动装配？
    private RedisTemplate<String, Object> redisTemplate;

    RedisUtil(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 指定key的缓存超时时间
     * @param key
     * @param time 超时时间（秒）
     * @return 设置成功返回true，失败返回false
     */
    public boolean expire(String key, long time){
        try {
            if (time > 0){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("设置缓存超时时间失败：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取超时时间，单位秒
     * @param key
     * @return 时间（秒），0代表永久生效
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("获取缓存时出现异常：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key
     */
    public void del(String ... key){//...为动态参数，可传入一个String也可以传入多个string相当于传入一个长度不固定的String数组
        try {
            if (key != null && key.length > 0){
                if (key.length == 1){
                    redisTemplate.delete(key[0]);
                }else {
                    redisTemplate.delete(CollectionUtils.arrayToList(key));
                }
            }
        } catch (Exception e) {
            log.error("删除缓存失败：" + e.getMessage());
            e.printStackTrace();
        }
    }


    //=======================================String=================================

    /**
     * 根据key获取缓存
     * @param key
     * @return
     */
    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 放入缓存并设置超时时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value,long time){

    }
}
