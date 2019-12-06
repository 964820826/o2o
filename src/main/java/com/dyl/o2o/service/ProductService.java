package com.dyl.o2o.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyl.o2o.domain.ProductDO;

import java.util.List;

/** 商品相关业务层接口
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:03
 */
public interface ProductService extends IService<ProductDO>{

    List<ProductDO> page(ProductDO productDO, int pageIndex, int pagesize);
}
