package com.dyl.o2o.exception;

import com.dyl.o2o.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/** 全局异常处理
 * @author ：dyl
 * @date ：Created in 2019/10/10 16:06
 */
@RestControllerAdvice //全局处理异常类的注解（
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R APIExceptionHandler(Exception e){
        log.error("发生异常：" + e.getMessage());
        return R.error(BAD_REQUEST.value(),"提交失败");
    }
}
