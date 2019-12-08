package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyl.o2o.common.util.security.JWTUser;
import com.dyl.o2o.dao.UserDao;
import com.dyl.o2o.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** 供权限框架调用
 * @author ：dyl
 * @date ：Created in 2019/12/8 23:13
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDao userDao;

    /**
     * 根据用户名获取用户对象
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        //todo 可在此处设置查询条件
        UserDO user = userDao.selectOne(new QueryWrapper<>(userDO));
        return new JWTUser(user);
    }
}
