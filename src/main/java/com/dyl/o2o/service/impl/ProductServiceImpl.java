package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.common.util.PageUtil;
import com.dyl.o2o.dao.ProductDao;
import com.dyl.o2o.dao.ProductImgDao;
import com.dyl.o2o.domain.ProductDO;
import com.dyl.o2o.domain.ProductImgDO;
import com.dyl.o2o.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 商品业务实现层
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:04
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductDO> implements ProductService {

    @Autowired
    ProductDao productDao;
    @Autowired
    ProductImgDao productImgDao;

    /**
     * 分页查询商品列表
     * @param productDO
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public IPage<ProductDO> page(ProductDO productDO, int pageIndex, int pageSize) {
        QueryWrapper<ProductDO> qw = new QueryWrapper<>();
        qw.like(productDO.getProductName()!=null,"product_name",productDO.getProductName());
        qw.eq(productDO.getProductCategoryId()!=null,"product_category_id",productDO.getProductCategoryId());
        qw.orderByDesc("priority");
        Page page = new Page(pageIndex,pageSize);
        IPage<ProductDO> productPage = productDao.selectPage(page,qw);
        return productPage;
    }

    /**
     * 根据id获取商品详情
     * @param id
     * @return
     */
    @Override
    public ProductDO getById(Long id) {
        ProductDO productCondition = new ProductDO();
        productCondition.setProductId(id);
        QueryWrapper<ProductDO> productQW = new QueryWrapper<>(productCondition);
        ProductDO productDO = productDao.selectOne(productQW);

        QueryWrapper qw = new QueryWrapper();
        qw.eq("product_id",id);
        qw.orderByDesc("priority");
        List<ProductImgDO> productImgDOList = productImgDao.selectList(qw);

        productDO.setProductImgList(productImgDOList);
        return productDO;
    }
}
