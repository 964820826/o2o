//package com.dyl.o2o.common.security;
//
//import com.dyl.o2o.common.security.JWTConfigBean;
//import com.dyl.o2o.common.util.JWTTokenUtil;
//import com.dyl.o2o.common.util.security.JWTUser;
//import com.dyl.o2o.dto.LoginUser;
//import com.dyl.o2o.service.impl.UserDetailsServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//
///**
// * 用于解析token，将用户的权限写入Spring security中
// *
// * @author ：dyl
// * @date ：Created in 2019/12/9 9:34
// */
//@Slf4j
//public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
//
//    @Autowired
//    UserDetailsServiceImpl userDetailsServiceImpl;
//    private AuthenticationManager authenticationManager;
//
//    public JWTLoginFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//        super.setFilterProcessesUrl("/login");
//    }
//
////    @Override
////    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
////        //将ServletRequest转为HttpServletRequest才可以取出请求头中的token
////        HttpServletRequest request = (HttpServletRequest) req;
////        //取出请求头中的token
////        String token = request.getHeader(JWTConfigBean.tokenHeader);
////        //从token中取出username
////        String username = JWTTokenUtil.getUsernameFromToken(token);
////
////        //若成功拿到username且会话中权限还未覆盖
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
////            //获取保存用户权限的实体类
////            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
////            //检查
////        }
////    }
//
//    /**
//     * 从输入流中获取登陆的信息，接收并解析用户凭证
//     * @param request
//     * @param response
//     * @return
//     * @throws AuthenticationException
//     */
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try{
//            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
//            Authentication authentication =authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginUser.getUsername(),
//                            loginUser.getPassword(),
//                            new ArrayList<>()));
//            return authentication;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 成功验证后调用的方法，如果成功，生成token返回
//     * @param request
//     * @param response
//     * @param chain
//     * @param authResult
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        JWTUser jwtUser = (JWTUser) authResult.getPrincipal();
//        System.out.println("jwtUser:" + jwtUser.toString());
//        String token = JWTTokenUtil.createAccessToken(jwtUser);
//        log.info("生成Token：" + token);
//        //此处生成的token只是单纯的token，按照jwt规定，请求格式应该是（Bearer token）
//        response.setHeader("token", JWTConfigBean.tokenPrefix + token);
//    }
//
//    /**
//     * 验证失败时调用的方法
//     * @param request
//     * @param response
//     * @param failed
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
//    }
//}
