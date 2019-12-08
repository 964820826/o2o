package com.dyl.o2o.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyl.o2o.domain.ProductCategoryDO;

import java.util.List;

/**
 * 商品类别业务层接口
 *
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:28
 */
public interface ProductCategoryService extends IService<ProductCategoryDO> {
    List<ProductCategoryDO> list(ProductCategoryDO productCategoryDO);
}
