package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.common.util.ImageUtil;
import com.dyl.o2o.common.util.PageUtil;
import com.dyl.o2o.dao.ProductDao;
import com.dyl.o2o.dao.ProductImgDao;
import com.dyl.o2o.domain.ProductDO;
import com.dyl.o2o.domain.ProductImgDO;
import com.dyl.o2o.service.ProductImgService;
import com.dyl.o2o.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/** 商品业务实现层
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:04
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductDO> implements ProductService {

    @Autowired
    ProductDao productDao;
    @Autowired
    ProductImgDao productImgDao;
    @Autowired
    ProductImgService productImgService;

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

    /**
     * 更新数据库信息
     * @param productThumbnailPath
     * @param productDetailImgPathList
     * @param productDO
     */
    @Override
    @Transactional
    public void updateDetail(String productThumbnailPath, List<String> productDetailImgPathList, ProductDO productDO){
        //1.将原图片地址都记录下来，若更新成功则将原图删除
        String oldThumbnailPath = productDao.selectById(productDO.getProductId()).getProductThum();
        List<String> oldDetailImgPathList = productImgDao.selectImgPathListByProductId(productDO.getProductId());

        //2.更新商品信息及图片地址
        //设置商品缩略图
        productDO.setProductThum(productThumbnailPath);
        //批量修改商品详情图片
        productImgService.batchModifyProductImg(productDO.getProductId(),productDetailImgPathList);
        //更新商品信息
        updateById(productDO);

        //3.删除原图，若发生异常删除不了就记录日志，不予删除
        try{
            ImageUtil.deleteFile(oldThumbnailPath);
            ImageUtil.deleteFileList(oldDetailImgPathList);
        }catch (Exception e){
            log.error("删除图片失败：" + e.getMessage());
        }
    }

    /**
     * 根据商品id删除商品信息
     * @param productId
     */
    @Override
    @Transactional
    public void remove(Long productId) {
        //1.获取商品图片地址
        List<String> imgPathList = productImgDao.selectImgPathListByProductId(productId);

        //3.删除数据库保存的商品图片信息
        productImgDao.deleteByProductId(productId);

        //2.删除商品信息
        productDao.deleteById(productId);

        //4.删除商品详情图片文件
        try {
            ImageUtil.deleteFileList(imgPathList);
        } catch (Exception e) {
            log.error("删除图片失败：" + e.getMessage());
        }
    }
}
