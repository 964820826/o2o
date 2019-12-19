package com.dyl.o2o.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyl.o2o.domain.RoleDO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 角色相关业务层接口
 *
 * @author ：dyl
 * @date ：Created in 2019/12/16 22:57
 */
public interface RoleService extends IService<RoleDO> {

    /**
     * 通过角色id获取角色权限
     * @param roleId
     * @return
     */
    Collection<GrantedAuthority> getRolePermission(Long roleId);
}
