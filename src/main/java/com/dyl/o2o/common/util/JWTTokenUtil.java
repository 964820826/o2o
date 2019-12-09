package com.dyl.o2o.common.util;

import com.alibaba.fastjson.JSON;
import com.dyl.o2o.common.config.JWTConfigBean;
import com.dyl.o2o.common.util.security.JWTUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/** JWT(Json Web Token)工具类
 * @author ：dyl
 * @date ：Created in 2019/12/8 18:40
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 生成token
     * @param jWTUser 用户安全实体
     * @return token
     */
    public static String createAccessToken(JWTUser jWTUser){
        //登陆成功生成JWT
        String token = Jwts.builder()
                //用户id
                .setId(jWTUser.getUserId().toString())
                //主题
                .setSubject(jWTUser.getUsername())
                //签发时间
                .setIssuedAt(new Date())
                //签发者
                .setIssuer("dyl")
                //自定义属性，放入用户拥有权限
                .claim("authorities", JSON.toJSONString(jWTUser.getAuthorities()))
                //失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfigBean.expiration*1000))
                //签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JWTConfigBean.secret)
                .compact();
        log.debug("生成Token：" + token);
        return token;
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 是否已过期
     * @param token
     * @return 过期返回true，未过期返回false
     */
    public static boolean isExpiration(String token){
        //获取设置的超时时间点，该时间点在当前时间之前则为超时
        return getTokenBody(token).getExpiration().before(new Date());
    }

    public static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(JWTConfigBean.secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
