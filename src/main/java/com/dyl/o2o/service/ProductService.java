package com.dyl.o2o.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dyl.o2o.domain.ProductDO;

import java.util.List;

/** 商品相关业务层接口
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:03
 */
public interface ProductService extends IService<ProductDO>{

    /**
     * 分页查询商品列表
     * @param productDO
     * @param pageIndex
     * @param pagesize
     * @return
     */
    IPage<ProductDO> page(ProductDO productDO, int pageIndex, int pagesize);

    /**
     * 根据id获取商品详情
     * @param id
     * @return
     */
    ProductDO getById(Long id);

    /**
     * 更新商品信息
     * @param productThumbnailPath
     * @param productDetailImgPathList
     * @param productDO
     */
    void updateDetail(String productThumbnailPath, List<String> productDetailImgPathList, ProductDO productDO);

    /**
     * 新增商品信息
     * @param productDetailImgPathList
     * @param productDO
     */
    void save(List<String> productDetailImgPathList, ProductDO productDO);

    /**
     * 根据商品id删除商品信息
     * @param productId
     */
    void remove(Long productId);
}
