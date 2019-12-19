package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.common.security.JWTUser;
import com.dyl.o2o.common.util.CaptchaUtil;
import com.dyl.o2o.common.util.JWTTokenUtil;
import com.dyl.o2o.domain.UserDO;
import com.dyl.o2o.dto.LoginUser;
import com.dyl.o2o.service.RoleService;
import com.dyl.o2o.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/** 用户相关控制层
 * @author ：dyl
 * @date ：Created in 2019/12/9 15:01
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    /**
     * 普通用户注册
     * @param loginUser
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("注册用户")
    @ApiImplicitParam(name = "captcha", value = "验证码")
    //todo 第三方账户授权，注册时填写其他字段
    public R registerUser(LoginUser loginUser, String captcha, HttpServletRequest request){
        //校验验证码
        if (!CaptchaUtil.checkVerifyCode(captcha,request.getSession())){
            return R.error(ResultCode.CAPTCHA_FAIL);
        }

        //业务处理，若业务出现异常service层直接抛出，controller层也会抛出，由统一异常处理器处理
        UserDO userDO = userService.addUser(loginUser);
        //获取用户角色标志
        String userRole = roleService.getById(userDO.getRoleId()).getRoleMark();
        //根据用户生成token
        JWTUser jwtUser = new JWTUser(userDO);
        jwtUser.setAuthorities(roleService.getRolePermission(jwtUser.getUserId()));
        String token = JWTTokenUtil.createAccessToken(jwtUser);

        //构建返回结果
        Map resultMap = new HashMap();
        resultMap.put("token", token);
        resultMap.put("role", userRole);
        return R.success(resultMap);
    }

}
