package com.dyl.o2o.common.security;

import com.dyl.o2o.common.security.JWTConfigBean;
import com.dyl.o2o.common.util.JWTTokenUtil;
import com.dyl.o2o.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

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

    /**
     * 获取token，校验合法性
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("鉴权url： '{}'",request.getRequestURI());

        //获取请求头
        String tokenHeader = request.getHeader(JWTConfigBean.tokenHeader);
//        //如果请求头中没有Authorization信息则直接放行
//        if (tokenHeader == null || !tokenHeader.startsWith(JWTConfigBean.tokenPrefix)){
//            chain.doFilter(request, response);
//            return;
//        }
        //若请求头格式正确
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            //从请求头中获取token，进而获取用户名
            String token = tokenHeader.substring(7);
            String username = JWTTokenUtil.getUsername(token);
            //若有用户名，则通过用户名获取该用户
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        chain.doFilter(request,response);
//        //如果请求头中有token，则进行解析，并设置认证信息
//        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
//        super.doFilterInternal(request, response, chain);
    }

//    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader){
//        String token = tokenHeader.replace(JWTConfigBean.tokenPrefix, "");
//        String username = JWTTokenUtil.getUsername(token);
//        if (username != null){
//            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//        }
//        return null;
//    }
}
