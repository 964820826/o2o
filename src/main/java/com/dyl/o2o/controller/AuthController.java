package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.common.security.JWTConfigBean;
import com.dyl.o2o.common.util.JWTTokenUtil;
import com.dyl.o2o.common.util.security.JWTUser;
import com.dyl.o2o.domain.UserDO;
import com.dyl.o2o.dto.LoginUser;
import com.dyl.o2o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/** 登陆鉴权相关控制层
 * @author ：dyl
 * @date ：Created in 2019/12/11 21:44
 */
@RestController
public class AuthController {

    @Autowired
    UserService userService;

    /**
     * 登陆
     * @param loginUser
     * @return
     */
    @PostMapping("/login")
    public R login(LoginUser loginUser){
        //todo 校验输入参数合法性
        //查数据库获取用户信息
        UserDO userDO = userService.getUserByUsername(loginUser.getUsername());
        //根据用户生成token
        String token = JWTTokenUtil.createAccessToken(new JWTUser(userDO));
        return R.success(token);
    }
}
