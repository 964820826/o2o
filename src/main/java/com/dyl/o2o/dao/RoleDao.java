package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.MenuDO;
import com.dyl.o2o.domain.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Set;

/** 角色相关数据层
 * @author ：dyl
 * @date ：Created in 2019/12/13 14:06
 */
@Mapper
@Component
public interface RoleDao extends BaseMapper<RoleDO> {

    /**
     * 通过角色id获取角色的权限
     * @param id
     * @return
     */
    @Select("select * from sys_menu where (select menu_id from sys_role_menu where menu_id=${id} and menu_id = sys_menu.menu_id)")
    Set<MenuDO> getPermissionByRoleId(Long id);
}
