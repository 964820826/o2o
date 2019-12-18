package com.dyl.o2o.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyl.o2o.domain.ShopDO;

import java.util.List;

/**
 * 店铺相关的service层
 *
 * @author ：dyl
 * @date ：Created in 2019/10/7 12:08
 */
public interface ShopService extends IService<ShopDO> {

    /**
     * 更新店铺信息
     *
     * @param shopDO
     */
    void update(ShopDO shopDO);

    /**
     * 查询店铺列表
     *
     * @param shopDO
     * @return
     */
    List<ShopDO> list(ShopDO shopDO);

    /**
     * 根据店铺拥有者的id获取店铺信息
     *
     * @param userId
     * @return
     */
    ShopDO getShopByOwnerId(Long userId);
}
