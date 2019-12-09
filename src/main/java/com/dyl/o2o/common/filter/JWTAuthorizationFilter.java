package com.dyl.o2o.common.filter;

import com.dyl.o2o.common.config.JWTConfigBean;
import com.dyl.o2o.common.util.JWTTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/** 鉴权过滤器
 * @author ：dyl
 * @date ：Created in 2019/12/9 13:59
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取请求头
        String tokenHeader = request.getHeader(JWTConfigBean.tokenPrefix);
        //如果请求头中没有Authorization信息则直接放行
        if (tokenHeader == null || !tokenHeader.startsWith(JWTConfigBean.tokenPrefix)){
            chain.doFilter(request, response);
            return;
        }
        //如果请求头中有token，则进行解析，并设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request, response, chain);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader){
        String token = tokenHeader.replace(JWTConfigBean.tokenPrefix, "");
        String username = JWTTokenUtil.getUsername(token);
        if (username != null){
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        }
        return null;
    }
}
