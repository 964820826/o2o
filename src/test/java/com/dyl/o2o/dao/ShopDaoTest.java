package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyl.o2o.BaseTest;
import com.dyl.o2o.domain.ShopDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/** 店铺相关的Dao层测试
 * @author ：dyl
 * @date ：Created in 2019/10/4 12:01
 */
public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testInsertShop(){
        ShopDO shop = new ShopDO();
        shop.setShopName("王二烧烤");
        shop.setAreaId(2L);
        shop.setOwnerId(1L);
        shop.setShopCategoryId(10L);
        shop.setCreateTime(new Date());
        int count = shopDao.insert(shop);
        System.out.println("添加 " + count + " 条记录！");
    }

    @Test
    public void testUpdateShop(){
        ShopDO shop = new ShopDO();
        shop.setShopId(36L);
        shop.setShopName("王wu烧烤");
        int count = shopDao.updateById(shop);
        System.out.println("更新 " + count + " 条记录！");
    }

    @Test
    public void testSelectList(){
        Page<ShopDO> page = new Page<ShopDO>(1,5);
        ShopDO shopDO = new ShopDO();
        shopDO.setShopName("王二烤鱼");
        QueryWrapper wrapper = new QueryWrapper(shopDO);

        IPage<ShopDO> o = shopDao.selectPage(page,wrapper);
        List<ShopDO> list = o.getRecords();
        System.out.println("查询到"+list.size()+ "项结果");
        for (ShopDO shop: list) {
            System.out.println(shop.toString());
        }
        System.out.println("ok");
    }
}
