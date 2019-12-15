package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.common.util.JWTTokenUtil;
import com.dyl.o2o.domain.UserDO;
import com.dyl.o2o.dto.LoginUser;
import com.dyl.o2o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 用户相关控制层
 * @author ：dyl
 * @date ：Created in 2019/12/9 15:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //用于加密
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 普通用户注册
     * @param loginUser
     * @return
     */
    @PostMapping("/register")
    public R registerUser(LoginUser loginUser){
        userService.addUser(loginUser);
        return R.success();
    }

}
