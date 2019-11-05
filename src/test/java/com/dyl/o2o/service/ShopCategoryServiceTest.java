package com.dyl.o2o.service;

import com.dyl.o2o.BaseTest;
import com.dyl.o2o.domain.ShopCategoryDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ：dyl
 * @date ：Created in 2019/10/11 21:43
 */
public class ShopCategoryServiceTest extends BaseTest {

    @Autowired
    ShopCategoryService shopCategoryService;

    @Test
    public void testGetShopCategoryList(){
        ShopCategoryDO condition = new ShopCategoryDO();
        List<ShopCategoryDO> shopCategoryDOList = shopCategoryService.selectShopCategoryList(condition);
        for (ShopCategoryDO shopCategory : shopCategoryDOList){
            System.out.println(shopCategory.toString());
        }
    }
}
