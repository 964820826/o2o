package com.dyl.o2o.service;

import com.dyl.o2o.BaseTest;
import com.dyl.o2o.dao.ShopCategoryDao;
import com.dyl.o2o.domain.ShopCategoryDo;
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
        ShopCategoryDo condition = new ShopCategoryDo();
        List<ShopCategoryDo> shopCategoryDoList = shopCategoryService.selectShopCategoryList(condition);
        for (ShopCategoryDo shopCategory : shopCategoryDoList){
            System.out.println(shopCategory.toString());
        }
    }
}
