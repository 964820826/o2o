package com.dyl.o2o.common.security;

import com.alibaba.fastjson.JSON;
import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登陆时出现异常的结果处理类
 *
 * @author ：dyl
 * @date ：Created in 2019/12/10 15:58
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    /**
     * 出现登陆异常时security框架自动调用，也可在登陆校验时手动调用返回结果
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException{
        response.setContentType("text/html;charset=utf-8");//避免乱码
        String result;
        if (e instanceof DisabledException){//过期
            result = JSON.toJSONString(R.error(ResultCode.USER_EXPIRE));
            response.getWriter().write(e.getMessage());
        }else if (e instanceof LockedException){
            result = JSON.toJSONString(R.error(ResultCode.ACCOUNT_USELESS));
        }else {
            result = JSON.toJSONString(R.error(ResultCode.NO_LOG_IN));
        }
        response.getWriter().write(result);
    }
}
