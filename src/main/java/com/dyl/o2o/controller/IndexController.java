package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.common.util.CaptchaUtil;
import com.dyl.o2o.common.util.EncryptUtil;
import com.dyl.o2o.common.util.JWTTokenUtil;
import com.dyl.o2o.common.security.JWTUser;
import com.dyl.o2o.domain.HeadLineDO;
import com.dyl.o2o.domain.RoleDO;
import com.dyl.o2o.dto.LoginUser;
import com.dyl.o2o.service.HeadLineService;
import com.dyl.o2o.service.UserService;
import com.dyl.o2o.service.impl.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 首页相关的控制层
 *
 * @author ：dyl
 * @date ：Created in 2019/11/29 20:23
 */
@Api(tags = "首页相关")
@RestController
public class IndexController {

    @Autowired
    HeadLineService headLineService;
    @Autowired
    UserService userService;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * 获取头条列表
     *
     * @param headLineDO
     * @return
     */
    @GetMapping("/headLine")
    @ApiOperation(value = "获取头条列表", notes = "需要以哪个字段查询填写哪个字段")
    public R queryHeadLine(HeadLineDO headLineDO) {
        List<HeadLineDO> headLineDOList = headLineService.list(headLineDO);
        return R.success(headLineDOList);
    }

    /**
     * 登陆
     * @param loginUser
     * @return
     */
    @PostMapping("/login")
    //被@Validated注解的参数后面必须紧跟BindingResult，否则BindingResult不生效
    public R login(@Validated LoginUser loginUser, BindingResult bindingResult, String verifyCodeActual, boolean needVerify, HttpSession session){
        //参数校验
        if (bindingResult.hasErrors()){
            return R.error(bindingResult.getFieldError().getDefaultMessage());
        }
        if (needVerify){
            if (!CaptchaUtil.checkVerifyCode(verifyCodeActual,session)){
                return R.error(ResultCode.CAPTCHA_FAIL);
            }
        }
        //todo 将登陆部分的代码移植到service层，controller层尽量少写业务逻辑
        //查数据库获取用户信息
        JWTUser jwtUser = (JWTUser) userDetailsServiceImpl.loadUserByUsername(loginUser.getUsername());
        //核对密码
        if (!jwtUser.getPassword().equals(EncryptUtil.encryptPassword(loginUser.getPassword()))){
            return R.error(ResultCode.PASSWORD_ERROR);
        }
        //根据用户生成token
        String token = JWTTokenUtil.createAccessToken(jwtUser);
        //获取用户角色
        Set <RoleDO> userRoleSet = userService.getUserRoleByUserId(jwtUser.getUserId());
        String userRole = userRoleSet.iterator().next().getRoleName();
        //构建返回结果
        Map resultMap = new HashMap();
        resultMap.put("token", token);
        resultMap.put("role",userRole);
        return R.success(resultMap);
    }
}
