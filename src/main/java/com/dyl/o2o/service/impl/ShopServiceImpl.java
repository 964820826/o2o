package com.dyl.o2o.service.impl;

import com.dyl.o2o.dao.ShopDao;
import com.dyl.o2o.domain.ShopDo;
import com.dyl.o2o.service.ShopService;
import com.dyl.o2o.util.ImageUtil;
import com.dyl.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;

/** 店铺相关的service层实现类
 * @author ：dyl
 * @date ：Created in 2019/10/7 12:11
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopDao shopDao;

    @Override
    @Transactional //事务注解，发生运行时异常则回滚
    public void save(ShopDo shop, File shopImg) {
        //todo 空值判断 service内部方法调用暂且不做参数验证
        shop.setEnableStatus(0);
        shop.setCreateTime(new Date());
        int effectedNum = shopDao.insert(shop);
        if(effectedNum <= 0){
            throw new RuntimeException("店铺创建异常！");
        }else{
            //添加店铺成功
            if(shopImg != null){
                //添加店铺图片到数据库并将地址设置到shop中
                try{
                    addShopImg(shop, shopImg);
                }catch (Exception e){
                    throw new RuntimeException("生成店铺缩略图失败");
                }
                //更新店铺的图片地址
                effectedNum = shopDao.updateById(shop);
                if (effectedNum <= 0){
                    throw new RuntimeException("更新店铺图片失败！");
                }
            }
        }
    }

    /**
     * 添加店铺图片
     * @param shop
     * @param shopImg
     */
    private void addShopImg(ShopDo shop, File shopImg) {
        //获取生成图片的相对路径
        String imgFolderRelativeAddr = PathUtil.getShopImgFolderRelativePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg,imgFolderRelativeAddr);
        shop.setShopImg(shopImgAddr);
    }
}
