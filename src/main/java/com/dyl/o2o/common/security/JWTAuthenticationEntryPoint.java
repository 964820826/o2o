package com.dyl.o2o.common.security;

import com.alibaba.fastjson.JSON;
import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登陆结果处理类
 *
 * @author ：dyl
 * @date ：Created in 2019/12/10 15:58
 */
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.getWriter().write(JSON.toJSONString(R.error(ResultCode.NO_LOG_IN)));
    }
}
