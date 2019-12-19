package com.dyl.o2o;

import com.dyl.o2o.domain.ShopDO;
import com.dyl.o2o.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author ：dyl
 * @date ：Created in 2019/12/19 10:23
 */
public class ServiceTest extends BaseTest {

    @Autowired
    ShopService shopService;

    @Test
    public void test(){
        List<ShopDO> shopDOList = shopService.list(new ShopDO());
        System.out.println(shopDOList);
    }
}
