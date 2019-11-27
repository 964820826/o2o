package com.dyl.o2o.common.exception;

import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
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
        log.error("未获取到参数异常：" + e.getMessage());
        return R.error(ResultCode.NULL_PARAM);
    }

    @ExceptionHandler(value = Exception.class)
    public R NullExceptionHandler(Exception e){
        log.error("内部异常：" + e.getMessage());
        return R.error(ResultCode.INNER_ERROR);
    }
}
