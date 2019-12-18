package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.dao.ShopCategoryDao;
import com.dyl.o2o.dao.ShopDao;
import com.dyl.o2o.domain.ShopCategoryDO;
import com.dyl.o2o.domain.ShopDO;
import com.dyl.o2o.service.ShopService;
import com.dyl.o2o.common.util.ImageUtil;
import com.dyl.o2o.common.util.PathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 店铺相关的service层实现类
 *
 * @author ：dyl
 * @date ：Created in 2019/10/7 12:11
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopDao, ShopDO> implements ShopService {

    @Autowired
    ShopDao shopDao;
    @Autowired
    ShopCategoryDao shopCategoryDao;

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
     *
     * @param shopDO
     * @return
     */
    @Override
    public boolean save(ShopDO shopDO) {
        shopDO.setEnableStatus(0);
        shopDO.setCreateTime(new Date());
        int effectedNum = shopDao.insert(shopDO);
        return effectedNum > 0;
    }

    /**
     * 更新店铺信息
     *
     * @param shopDO
     */
    @Override
    public void update(ShopDO shopDO) {
        //获取原来保存的图片地址
        String oldImgPath = shopDao.selectById(shopDO.getShopId()).getShopImg();
        //更新数据库数据
        shopDao.updateById(shopDO);
        //删除原图片
        ImageUtil.deleteImg(oldImgPath);
    }

    /**
     * 查询店铺列表，若输入的店铺区域类别为一级店铺类别，则获取所有该一级类别下的店铺列表
     *
     * @param shopDO
     * @return
     */
    @Override
    public List<ShopDO> list(ShopDO shopDO) {
        List<ShopDO> shopDOList = new ArrayList<>();
        //判断店铺类别的输入条件是一级类别还是二级类别
        if (shopDO.getShopCategoryId() != null && shopDO.getShopCategoryId() > 0) {
            ShopCategoryDO shopCategoryCondition = new ShopCategoryDO();
            shopCategoryCondition.setParentId(shopDO.getShopCategoryId());
            //获取下级店铺类别
            List<ShopCategoryDO> shopCategoryDOList = shopCategoryDao.selectList(new QueryWrapper<>(shopCategoryCondition));
            //有下级类别，则输入的是一级店铺类别，获取该类别下所有二级类别
            if (shopCategoryDOList.size() != 0) {
                //需要修改查询条件，故新建一个对象用来存放查询条件，不改变原shopDO数据
                ShopDO shopCondition = new ShopDO();
                BeanUtils.copyProperties(shopDO, shopCondition);
                //根据店铺类别获取该店铺类别下的店铺，合并到一个列表中
                for (ShopCategoryDO shopCategoryDO : shopCategoryDOList) {
                    //todo 值传递还是引用传递，此处的情况符合引用传递
                    shopCondition.setShopCategoryId(shopCategoryDO.getShopCategoryId());
                    List<ShopDO> shopDOS = shopDao.selectList(shopCondition);
                    shopDOList.addAll(shopDOS);
                }
            } else {
                //查询二级类别id查询店铺
                shopDOList = shopDao.selectList(shopDO);
            }
        } else {
            //不依据店铺类别查询
            shopDOList = shopDao.selectList(shopDO);
        }
        return shopDOList;
    }

    /**
     * 根据店铺拥有者的id获取店铺信息
     *
     * @param userId
     * @return
     */
    @Override
    public ShopDO getShopByOwnerId(Long userId) {
        return shopDao.getByOwnerId(userId);
    }

}
