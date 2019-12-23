package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.dao.ProductImgDao;
import com.dyl.o2o.domain.ProductImgDO;
import com.dyl.o2o.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 商品图片业务实现层
 * @author ：dyl
 * @date ：Created in 2019/12/21 0:11
 */
@Service
public class ProductImgServiceImpl extends ServiceImpl<ProductImgDao, ProductImgDO> implements ProductImgService {

    @Autowired
    ProductImgDao productImgDao;

    /**
     * 批量
     * @param productId
     * @param productDetailImgPathList
     */
    @Override
    @Transactional
    public void batchModifyProductImg(Long productId, List<String> productDetailImgPathList) {
        //获取原商品的详情图id，若生成详情图无异常，则将旧图片删除
        List<Long> idList = productImgDao.selectImgIdListByProductId(productId);

        //生成数据库对象，保存到数据库
        this.batchAddProductImg(productId,productDetailImgPathList);

        //保存成功，删除数据库中旧图片信息
        if (idList.size() != 0){
            productImgDao.deleteBatchIds(idList);
        }
    }

    /**
     * 批量新增商品明细图片
     * @param productId
     * @param productDetailImgPathList
     */
    @Override
    public void batchAddProductImg(Long productId, List<String> productDetailImgPathList) {
        ProductImgDO productImgDO = new ProductImgDO();
        productImgDO.setProductId(productId);
        productImgDO.setCreateTime(new Date());
        //遍历图片地址list，生成添加数据库的对象，调用dao层修改数据库
        for (String imgPath: productDetailImgPathList) {
            productImgDO.setProductImgAddr(imgPath);
            //设置权限，从100开始，依次下降
            productImgDO.setPriority(100-productDetailImgPathList.indexOf(imgPath));
            //调用dao层方法添加数据库
            productImgDao.insert(productImgDO);
        }
    }
}
