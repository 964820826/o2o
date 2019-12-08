package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyl.o2o.BaseTest;
import com.dyl.o2o.domain.ProductDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：dyl
 * @date ：Created in 2019/12/7 13:46
 */
public class ProductDaoTest extends BaseTest {
    @Autowired
    ProductDao productDao;

    @Test
    public void testPageProduct(){
        Page<ProductDO> page = new Page<>(1,5);
        QueryWrapper<ProductDO> wrapper = new QueryWrapper<>(new ProductDO());
        IPage<ProductDO> productDOIPage = productDao.selectPage(page,wrapper);
    }
}
