package com.dyl.o2o.common.util;

import com.alibaba.fastjson.JSON;
import com.dyl.o2o.common.security.JWTConfigBean;
import com.dyl.o2o.common.security.JWTUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/** JWT(Json Web Token)工具类
 * @author ：dyl
 * @date ：Created in 2019/12/8 18:40
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 根据用户信息生成token
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
                //失效时间点，当前时间+有效时间（毫秒）
                .setExpiration(new Date(System.currentTimeMillis() + (JWTConfigBean.expiration*1000)))
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
    public static String getUsernameFromToken(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 检查token是否已过期
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

    /**
     * 获取当前登陆的用户
     * @return
     */
    public static JWTUser getCurrentUser(){
        //todo 待完善
        JWTUser user = (JWTUser) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return user;
    }
//    /**
//     * 检查 token 是否处于有效期内
//     * @param token
//     * @param userDetails
//     * @return
//     */
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        UserDetailImpl user = (UserDetailImpl) userDetails;
//        final String username = this.getUsernameFromToken(token);
//        final Date created = this.getCreatedDateFromToken(token);
//        return (username.equals(user.getUsername()) && !(this.isTokenExpired(token)) && !(this.isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset())));
//    }
//
//    /**
//     * 获得我们封装在 token 中的 token 创建时间
//     * @param token
//     * @return
//     */
//    public Date getCreatedDateFromToken(String token) {
//        Date created;
//        try {
//            final Claims claims = getTokenBody(token);
//            created = new Date((Long) claims.get("created"));
//        } catch (Exception e) {
//            created = null;
//        }
//        return created;
//    }
//
//    /**
//     * 获得我们封装在 token 中的 token 过期时间
//     * @param token
//     * @return
//     */
//    public Date getExpirationDateFromToken(String token) {
//        Date expiration;
//        try {
//            final Claims claims = this.getClaimsFromToken(token);
//            expiration = claims.getExpiration();
//        } catch (Exception e) {
//            expiration = null;
//        }
//        return expiration;
//    }
//
//    /**
//     * 检查当前时间是否在封装在 token 中的过期时间之后，若是，则判定为 token 过期
//     * @param token
//     * @return
//     */
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = this.getExpirationDateFromToken(token);
//        return expiration.before(this.generateCurrentDate());
//    }
//
//    /**
//     * 检查 token 是否是在最后一次修改密码之前创建的（账号修改密码之后之前生成的 token 即使没过期也判断为无效）
//     * @param created
//     * @param lastPasswordReset
//     * @return
//     */
//    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
//        return (lastPasswordReset != null && created.before(lastPasswordReset));
//    }
}

