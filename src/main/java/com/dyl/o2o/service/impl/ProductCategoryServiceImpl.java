package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.dao.ProductCategoryDao;
import com.dyl.o2o.domain.ProductCategoryDO;
import com.dyl.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类别业务层实现类
 *
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:29
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryDao, ProductCategoryDO> implements ProductCategoryService {

    @Autowired
    ProductCategoryDao productCategoryDao;

    /**
     * 根据条件获取商品类别
     * @param productCategoryDO
     * @return
     */
    @Override
    public List<ProductCategoryDO> list(ProductCategoryDO productCategoryDO) {
        QueryWrapper queryWrapper = new QueryWrapper(productCategoryDO);
        queryWrapper.orderByDesc("priority");
        List productCategoryList = productCategoryDao.selectList(queryWrapper);
        return productCategoryList;
    }
}
