package com.dyl.o2o.service;

import com.dyl.o2o.BaseTest;
import com.dyl.o2o.domain.ShopDo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Date;

/**
 * @author ：dyl
 * @date ：Created in 2019/10/8 20:39
 */
public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testSave(){
        ShopDo shop = new ShopDo();
        shop.setShopName("王二烤鱼");
        shop.setAreaId(3L);
        shop.setOwnerId(8L);
        shop.setShopCategoryId(12L);
        shop.setEnableStatus(0);
        shop.setCreateTime(new Date());

        File file = new File("E:\\IDEAWorkspace\\git\\o2o\\target\\classes\\static\\img\\1.jpg");
        shopService.save(shop,file);
    }
}
