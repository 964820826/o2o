package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.RoleDO;
import com.dyl.o2o.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Set;

/** 用户相关数据层
 * @author ：dyl
 * @date ：Created in 2019/12/8 23:16
 */
@Mapper
@Component
public interface UserDao extends BaseMapper<UserDO> {

    @Select("select * from sys_role where exists (select role_id from sys_user_role where user_id = #{id} and role_id = sys_role.role_id)")
    Set<RoleDO> getUserRole(Long id);
}
