package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.dao.UserDao;
import com.dyl.o2o.domain.UserDO;
import com.dyl.o2o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 用户相关业务层实现类
 * @author ：dyl
 * @date ：Created in 2019/12/9 15:59
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserDO> implements UserService {

    @Autowired
    UserDao userDao;

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    @Override
    public UserDO getUserByUsername(String username) {
        UserDO userCondition = new UserDO();
        userCondition.setUsername(username);
        QueryWrapper<UserDO> qw = new QueryWrapper<>(userCondition);
        UserDO userDO = userDao.selectOne(qw);
        return userDO;
    }
}
