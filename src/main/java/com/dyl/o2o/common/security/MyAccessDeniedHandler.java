package com.dyl.o2o.common.security;

import com.alibaba.fastjson.JSON;
import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：dyl
 * @date ：Created in 2019/12/12 15:53
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.getWriter().write(JSON.toJSONString(R.error(ResultCode.NO_LOG_IN)));
    }
}
