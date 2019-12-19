package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.common.exception.UserAlreadyExistException;
import com.dyl.o2o.common.security.JWTUser;
import com.dyl.o2o.common.util.EncryptUtil;
import com.dyl.o2o.dao.UserDao;
import com.dyl.o2o.domain.UserDO;
import com.dyl.o2o.dto.LoginUser;
import com.dyl.o2o.service.RoleService;
import com.dyl.o2o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/** 用户及权限相关的业务层
 * @author ：dyl
 * @date ：Created in 2019/12/8 23:13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserDO> implements UserDetailsService, UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    RoleService roleService;

    /**
     * 根据用户名获取用户对象
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userCondition = new UserDO();
        userCondition.setUsername(username);
        UserDO user = userDao.selectOne(new QueryWrapper<>(userCondition));
        if (user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        JWTUser jwtUser = new JWTUser(user);
        jwtUser.setAuthorities(roleService.getRolePermission(jwtUser.getUserId()));
        return jwtUser;
    }

    /**
     * 新增用户
     * @param loginUser
     */
    @Override
    @Transactional
    public UserDO addUser(LoginUser loginUser) {
        //检查该用户名是否存在
        if (userDao.checkUsernameExist(loginUser.getUsername())){
            //用户名存在则抛异常
            throw new UserAlreadyExistException("");
        }
        UserDO userDO = new UserDO(loginUser);
        userDO.setPassword(EncryptUtil.encryptPassword(userDO.getPassword()));
        userDO.setCreateTime(new Date());
        //默认新增用户的角色为消费者
        userDO.setRoleId(2L);
        if (userDao.insert(userDO) != 1){
            throw new RuntimeException("插入用户信息失败");
        }
        return userDO;
    }


}
