package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.common.util.security.JWTUser;
import com.dyl.o2o.dao.RoleDao;
import com.dyl.o2o.dao.UserDao;
import com.dyl.o2o.domain.MenuDO;
import com.dyl.o2o.domain.RoleDO;
import com.dyl.o2o.domain.UserDO;
import com.dyl.o2o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** 用户及权限相关的业务层
 * @author ：dyl
 * @date ：Created in 2019/12/8 23:13
 */
@Service
public class UserDetailsServiceImpl extends ServiceImpl<UserDao, UserDO> implements UserDetailsService, UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;

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
        user.setRoles(userDao.getUserRole(user.getUserId()));
        JWTUser jwtUser = new JWTUser(user);
        jwtUser.setAuthorities(getRolePermission(jwtUser.getRoles()));
        return new JWTUser(user);
    }

    /**
     * 获取角色的权限
     * @param roles
     * @return
     */
    private Collection<GrantedAuthority> getRolePermission(Set<RoleDO> roles) {
        Set<MenuDO> permissions = new HashSet<>();
        for (RoleDO role : roles) {
            permissions.addAll(roleDao.getPermissionByRoleId(role.getRoleId()));
        }


    }
}
