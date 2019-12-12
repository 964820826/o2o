package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.common.util.EncryptUtil;
import com.dyl.o2o.common.util.JWTTokenUtil;
import com.dyl.o2o.common.util.security.JWTUser;
import com.dyl.o2o.domain.HeadLineDO;
import com.dyl.o2o.domain.UserDO;
import com.dyl.o2o.dto.LoginUser;
import com.dyl.o2o.service.HeadLineService;
import com.dyl.o2o.service.UserService;
import com.dyl.o2o.service.impl.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
    public R login(@Validated LoginUser loginUser, BindingResult bindingResult){
        //参数校验
        if (bindingResult.hasErrors()){
            return R.error(bindingResult.getFieldError().getDefaultMessage());
        }
        //todo 校验输入参数合法性
        //查数据库获取用户信息
        JWTUser jwtUser = (JWTUser) userDetailsServiceImpl.loadUserByUsername(loginUser.getUsername());
        //核对密码
        if (!jwtUser.getPassword().equals(EncryptUtil.encryptPassword(loginUser.getPassword()))){
            return R.error(ResultCode.PASSWORD_ERROR);
        }
        //根据用户生成token
        String token = JWTTokenUtil.createAccessToken(jwtUser);
        return R.success(token);
    }

    @PostMapping("/token")
    public String hello(String token){
        return JWTTokenUtil.getUsernameFromToken(token);
    }
}
