package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.dao.ProductDao;
import com.dyl.o2o.domain.ProductDO;
import com.dyl.o2o.service.ProductService;
import org.springframework.stereotype.Service;

/** 商品业务实现层
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:04
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductDO> implements ProductService {

}
