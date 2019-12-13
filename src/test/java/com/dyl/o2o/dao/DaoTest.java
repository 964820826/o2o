package com.dyl.o2o.dao;

import com.dyl.o2o.BaseTest;
import com.dyl.o2o.domain.RoleDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author ：dyl
 * @date ：Created in 2019/12/13 11:17
 */
public class DaoTest extends BaseTest {

    @Autowired
    UserDao userDao;

    @Test
    public void getRoleTest(){
        Set<RoleDO> roleDOS =  userDao.getUserRole(1L);
        roleDOS.stream().forEach(System.out::println);
    }
}
