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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("鉴权url： '{}'",request.getRequestURI());

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
            String username = JWTTokenUtil.getUsernameFromToken(token);
            //若有用户名，则通过用户名获取该用户
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //获取保存用户权限的实体类
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null){//todo 添加校验token是否过期
                    //若用户权限类不为空，则生成通过认证
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //将权限写入本次会话
                    //todo 给用户关联权限
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                //todo 添加判断用户关停的处理
//                if (!userDetails.isEnabled()){
//                    response.setCharacterEncoding("UTF-8");
//                    response.setContentType("application/json;charset=UTF-8");
//                    response.getWriter().print("{\"code\":\"452\",\"data\":\"\",\"message\":\"账号处于黑名单\"}");
//                    return;
//                }
            }
        }
        chain.doFilter(request,response);
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
