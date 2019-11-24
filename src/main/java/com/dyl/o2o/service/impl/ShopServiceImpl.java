package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.dao.ShopDao;
import com.dyl.o2o.domain.ShopDO;
import com.dyl.o2o.service.ShopService;
import com.dyl.o2o.common.util.ImageUtil;
import com.dyl.o2o.common.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/** 店铺相关的service层实现类
 * @author ：dyl
 * @date ：Created in 2019/10/7 12:11
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopDao, ShopDO>implements ShopService {

    @Autowired
    ShopDao shopDao;

//    @Override
//    @Transactional //事务注解，发生运行时异常则回滚
//    public void save(ShopDO shop) {
//        //todo 空值判断 service内部方法调用暂且不做参数验证
//        shop.setEnableStatus(0);
//        shop.setCreateTime(new Date());
//        int effectedNum = shopDao.insert(shop);
//        if(effectedNum <= 0){
//            throw new RuntimeException("店铺创建异常！");
//        }else{
//            //添加店铺成功
//            if(shopImg != null){
//                //添加店铺图片到数据库并将地址设置到shop中
//                try{
//                    addShopImg(shop, shopImg);
//                }catch (Exception e){
//                    throw new RuntimeException("生成店铺缩略图失败");
//                }
//                //更新店铺的图片地址
//                effectedNum = shopDao.updateById(shop);
//                if (effectedNum <= 0){
//                    throw new RuntimeException("更新店铺图片失败！");
//                }
//            }
//        }
//    }

    /**
     * 创建店铺
     * @param shopDO
     * @return
     */
    @Override
    public boolean save(ShopDO shopDO) {
        shopDO.setEnableStatus(0);
        shopDO.setCreateTime(new Date());
        int effectedNum = shopDao.insert(shopDO);
        if(effectedNum > 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 更新店铺
     * @param shopDO
     * @param img 新上传的图片（需转为File类型）
     */
    public void update(ShopDO shopDO, File img) throws Exception {
        //获取原图片地址
        String imgPath = shopDao.selectById(shopDO.getShopId()).getShopImg();
        //保存新图片
        ImageUtil.generateThumbnail(img);//生成缩略图替换掉原文件
        shopDO.setShopImg(img.getName());
        if (shopDao.updateById(shopDO) != 1){
            //若更新店铺失败则删除上传的图片
            img.delete();
            throw new Exception("更新店铺失败");
        }
        //删除原图片
        File oldImg = new File(imgPath);
        oldImg.delete();
    }
    @Override
    public boolean updateById(ShopDO entity) {
        return super.updateById(entity);
    }
}
