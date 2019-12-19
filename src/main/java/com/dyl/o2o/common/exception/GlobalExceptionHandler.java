package com.dyl.o2o.common.exception;

import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/** 全局异常处理
 * @author ：dyl
 * @date ：Created in 2019/10/10 16:06
 */
@RestControllerAdvice //全局处理异常类的注解
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IOException.class)
    public R IOExceptionHandler(IOException e){
        log.error("数据读取发生异常：" + e.getMessage());
        return R.error(ResultCode.IO_ERROR);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public R NullExceptionHandler(NullPointerException e){
        e.printStackTrace();
        log.error("未获取到参数异常：" + e.getMessage());
        return R.error(ResultCode.NULL_PARAM);
    }

    @ExceptionHandler(value = Exception.class)
    public R ExceptionHandler(Exception e){
        log.error("内部异常：" + e.getMessage());
        return R.error(ResultCode.INNER_ERROR);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public R AccessDeniedExceptionHandler(AccessDeniedException e){
        log.error("无操作权限：" + e.getMessage());
        return R.error(ResultCode.NO_AUTHORITY);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public R UserAreadyExistExceptionHandler(UserAlreadyExistException e){
        log.error("用户名已存在：" + e.getMessage());
        return R.error(ResultCode.USER_EXIST);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public R ExpiredJwtExceptionHandler(ExpiredJwtException e){
        log.error("token已过期" + e.getMessage());
        return R.error(ResultCode.USER_EXPIRE);
    }



//    @ExceptionHandler(value = AuthenticationException.class)
//    public R AuthenticationExceptionHandler(AuthenticationException  e){
//        log.error("未登陆：" + e.getMessage());
//        return R.error(ResultCode.NO_LOG_IN);
//    }

}
