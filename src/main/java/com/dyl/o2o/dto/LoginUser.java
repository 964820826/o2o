package com.dyl.o2o.dto;

import lombok.Data;

/** 用户登陆对象
 * @author ：dyl
 * @date ：Created in 2019/12/9 10:24
 */
@Data
public class LoginUser {
    //用户名
    private String username;

    //密码
    private String password;
}
