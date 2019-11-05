package com.dyl.o2o.service;

import com.dyl.o2o.domain.ShopDO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/** 店铺相关的service层
 * @author ：dyl
 * @date ：Created in 2019/10/7 12:08
 */
public interface ShopService {

//    void save(ShopDO shop, File shopImg);

    void save(ShopDO shopDO);
}
