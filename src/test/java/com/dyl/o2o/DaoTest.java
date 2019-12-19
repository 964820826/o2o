package com.dyl.o2o;

import com.dyl.o2o.BaseTest;
import com.dyl.o2o.dao.ShopDao;
import com.dyl.o2o.dao.UserDao;
import com.dyl.o2o.domain.RoleDO;
import com.dyl.o2o.domain.ShopDO;
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
    @Autowired
    ShopDao shopDao;

    @Test
    public void test(){
        ShopDO shopDO = shopDao.getByOwnerId(1L);
        System.out.println(shopDO.toString());
    }
}
