package com.dyl.o2o.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** 用户登陆对象
 * @author ：dyl
 * @date ：Created in 2019/12/9 10:24
 */
@Data
public class LoginUser {
    //用户名（不能为空，且去除空格后长度大于0）
    @NotBlank(message = "请输入用户名")
    @Length(min = 5, max = 10,message = "用户名长度需在5-10个字符之间")
    private String username;

    //密码
    @NotNull(message = "请输入密码")
    private String password;
}
