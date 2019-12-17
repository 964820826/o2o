package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.RoleDO;
import com.dyl.o2o.domain.UserDO;
import org.apache.ibatis.annotations.Insert;
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

    @Select("select count(*) from sys_user where username = #{username}")
    boolean checkUsernameExist(String username);
}
