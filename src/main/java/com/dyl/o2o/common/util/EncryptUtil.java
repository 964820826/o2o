package com.dyl.o2o.common.util;

import org.springframework.util.DigestUtils;

/**
 * 加密工具类
 *
 * @author ：dyl
 * @date ：Created in 2019/12/12 23:25
 */
public class EncryptUtil {

    /**
     * md5加密
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
