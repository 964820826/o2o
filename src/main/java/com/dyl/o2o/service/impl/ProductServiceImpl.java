package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.dao.ProductDao;
import com.dyl.o2o.domain.ProductDO;
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

    /**
     * 分页查询商品列表
     * @param productDO
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<ProductDO> page(ProductDO productDO, int pageIndex, int pageSize) {
        QueryWrapper queryWrapper = new QueryWrapper(productDO);
        queryWrapper.orderByDesc("priority");
        Page page = new Page(pageIndex,pageSize);
        IPage iPage = productDao.selectPage(page,queryWrapper);
        return iPage.getRecords();
    }
}
