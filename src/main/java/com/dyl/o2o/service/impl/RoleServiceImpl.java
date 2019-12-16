package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.dao.RoleDao;
import com.dyl.o2o.domain.MenuDO;
import com.dyl.o2o.domain.RoleDO;
import com.dyl.o2o.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/** 角色业务层实现类
 * @author ：dyl
 * @date ：Created in 2019/12/16 22:57
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleDO> implements RoleService {

    @Autowired
    RoleDao roleDao;

    /**
     * 获取角色的权限
     * @param roleId
     * @return
     */
    public Collection<GrantedAuthority> getRolePermission(Long roleId) {
        Set<MenuDO> permissions = roleDao.getPermissionByRoleId(roleId);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (MenuDO permission : permissions) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(permission.getPermission()));
        }
        return grantedAuthorityList;
    }
}
