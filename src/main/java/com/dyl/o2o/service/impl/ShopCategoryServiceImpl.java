package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyl.o2o.dao.ShopCategoryDao;
import com.dyl.o2o.domain.ShopCategoryDO;
import com.dyl.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 店铺类别service实现类
 * @author ：dyl
 * @date ：Created in 2019/10/11 21:31
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategoryDO> selectShopCategoryList(ShopCategoryDO condition) {
        //权重降序，名称模糊查询
        QueryWrapper queryWrapper = new QueryWrapper(condition);
        queryWrapper.orderByDesc("priority");
        if (condition.getShopCategoryName() != null){
            queryWrapper.like("shop_category_name",condition.getShopCategoryName());
        }
        //parentId为null则查询所有一级店铺类别（当condition里的parentId为空时，查询数据库里parent_id为null的数据）
        queryWrapper.isNull(condition.getParentId() == null,"parent_id");//条件查询里要写数据库中的字段
        return shopCategoryDao.selectList(queryWrapper);
    }
}
