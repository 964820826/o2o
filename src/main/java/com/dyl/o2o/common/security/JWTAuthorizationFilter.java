package com.dyl.o2o.common.security;

import com.dyl.o2o.common.util.JWTTokenUtil;
import com.dyl.o2o.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** token校验鉴权过滤器
 * 继承OncePerRequestFilter保证一次请求只调用一次doFilterInternal方法;如内部的forward不会再多执行一次
 * @author ：dyl
 * @date ：Created in 2019/12/9 13:59
 */
@Slf4j
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;

    /**
     * 获取token，校验合法性、设置权限
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("鉴权url： '{}'", request.getRequestURI());

        //获取请求头
        String tokenHeader = request.getHeader(JWTConfigBean.tokenHeader);
        String username = null;
        UserDetails userDetails = null;

        //校验token是否可用
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            //从请求头中获取token，进而获取用户名
            String token = tokenHeader.substring(7);
            username = JWTTokenUtil.getUsernameFromToken(token);
            if (JWTTokenUtil.isExpiration(token)) {
                entryPointUnauthorizedHandler.commence(request, response, new DisabledException("用户信息已过期，请重新登陆"));
            }
        }

        //校验账户是否可用
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //获取保存用户权限的实体类
            userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails.isAccountNonLocked()) {
                entryPointUnauthorizedHandler.commence(request, response, new DisabledException("该账户不可用"));
            }
        }

        //设置权限及通过校验
        if (userDetails != null){
            //若用户权限类不为空，则生成通过认证
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //将权限写入本次会话
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request,response);
    }

//        //若请求头不存在或不合规则，直接执行下个过滤器
//        try{
//            //从请求头中获取token，进而获取用户名
//            String token = tokenHeader.substring(7);
//            String username = JWTTokenUtil.getUsernameFromToken(token);
//            //获取保存用户权限的实体类
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            if (JWTTokenUtil.isExpiration(token)){
//                entryPointUnauthorizedHandler.commence(request,response,new DisabledException("用户信息已过期，请重新登陆"));
//            }
//            if (userDetails.isAccountNonLocked()){
//                entryPointUnauthorizedHandler.commence(request,response,new DisabledException("该账户不可用"));
//            }
//            //若用户权限类不为空，则生成通过认证
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            //将权限写入本次会话
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        }catch (Exception e){
//            log.error("用户信息认证失败：" + e.getMessage());
//            entryPointUnauthorizedHandler.commence(request,response,new DisabledException("用户信息认证失败"));
//        }
//        //执行下一个过滤器
//        chain.doFilter(request,response);
//
//
//        //若请求头格式正确
//        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
//            //从请求头中获取token，进而获取用户名
//            if (JWTTokenUtil.isExpiration(token)){
//                entryPointUnauthorizedHandler.commence(request,response,new DisabledException("用户信息已过期，请重新登陆"));
//            }
//            String token = tokenHeader.substring(7);
//            String username = JWTTokenUtil.getUsernameFromToken(token);
//            //若有用户名且没有设置过权限，则通过用户名获取该用户
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//                //获取保存用户权限的实体类
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                if (userDetails != null){
//                    //若用户权限类不为空，则生成通过认证
//                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    //将权限写入本次会话
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//                if (!userDetails.isEnabled()){
//                    response.setCharacterEncoding("UTF-8");
//                    response.setContentType("application/json;charset=UTF-8");
//                    response.getWriter().print("{\"code\":\"452\",\"data\":\"\",\"message\":\"账号处于黑名单\"}");
//                    return;
//                }
//            }
//        }
//        chain.doFilter(request,response);
//    }

}
