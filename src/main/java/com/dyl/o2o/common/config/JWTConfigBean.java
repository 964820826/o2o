package com.dyl.o2o.common.config;

import com.dyl.o2o.common.util.JWTTokenUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 讲jwt内的参数转为对象
 *
 * @author ：dyl
 * @date ：Created in 2019/12/9 10:45
 */
@Component
public class JWTConfigBean {
    //密钥
    public static String secret;
    //HeaderKEY
    public static String tokenHeader;
    //前缀
    public static String tokenPrefix;
    //从配置文件中获取过期时间
    public static Long expiration;

    @Value("${jwt.secret}")
    public static void setSecret(String secret) {
        JWTConfigBean.secret = secret;
    }

    @Value("${jwt.tokenHeader}")
    public static void setTokenHeader(String tokenHeader) {
        JWTConfigBean.tokenHeader = tokenHeader;
    }

    @Value("${jwt.tokenPrefix}")
    public static void setTokenPrefix(String tokenPrefix) {
        JWTConfigBean.tokenPrefix = tokenPrefix;
    }

    @Value("${jwt.expiration}")
    public static void setExpiration(Long expiration) {
        JWTConfigBean.expiration = expiration;
    }
}
