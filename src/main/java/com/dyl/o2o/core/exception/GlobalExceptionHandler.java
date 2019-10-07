package com.dyl.o2o.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** 全局异常处理类
 * @author ：dyl
 * @date ：Created in 2019/10/3 12:33
 */
@ControllerAdvice //标注为全局异常处理类
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class) //处理所有controller层抛出的Exception及其子类
    public String handlerException(Exception e){
        LOGGER.error(e.getMessage(),e);
        System.out.println("出现异常，请查看日志");
        return "";
    }
}
