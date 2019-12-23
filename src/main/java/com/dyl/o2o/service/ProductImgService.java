package com.dyl.o2o.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyl.o2o.domain.ProductImgDO;

import java.util.List;

/**
 * 商品图片业务层
 *
 * @author ：dyl
 * @date ：Created in 2019/12/21 0:10
 */
public interface ProductImgService extends IService<ProductImgDO> {

    //批量修改商品明细图片
    void batchModifyProductImg(Long productId, List<String> productDetailImgPathList);

    //批量新增商品明细图片
    void batchAddProductImg(Long productId, List<String> productDetailImgPathList);
}
