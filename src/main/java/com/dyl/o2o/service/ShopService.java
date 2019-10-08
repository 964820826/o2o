package com.dyl.o2o.service;

import com.dyl.o2o.domain.ShopDo;

import java.io.File;

/** 店铺相关的service层
 * @author ：dyl
 * @date ：Created in 2019/10/7 12:08
 */
public interface ShopService {

    void save(ShopDo shop, File shopImg);
}
