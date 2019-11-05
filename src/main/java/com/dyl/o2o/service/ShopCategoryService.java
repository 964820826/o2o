package com.dyl.o2o.service;

import com.dyl.o2o.domain.ShopCategoryDO;

import java.util.List;

/** 店铺类别的service层接口
 * @author ：dyl
 * @date ：Created in 2019/10/11 21:22
 */
public interface ShopCategoryService {

    /**
     * 获取店铺类别列表
     * @return
     */
    List<ShopCategoryDO> selectShopCategoryList(ShopCategoryDO condition);
}
