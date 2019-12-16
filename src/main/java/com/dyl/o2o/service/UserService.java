package com.dyl.o2o.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyl.o2o.domain.RoleDO;
import com.dyl.o2o.domain.UserDO;
import com.dyl.o2o.dto.LoginUser;

import java.util.Set;

/** 用户相关业务层
 * @author ：dyl
 * @date ：Created in 2019/12/9 15:17
 */
public interface UserService extends IService<UserDO> {

    UserDO addUser(LoginUser loginUser);
}
