package com.dyl.o2o.common.exception;

/** 用户已存在异常
 * @author ：dyl
 * @date ：Created in 2019/12/17 14:51
 */
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
