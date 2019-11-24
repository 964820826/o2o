package com.dyl.o2o.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dyl.o2o.domain.ShopDO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/** 店铺相关的service层
 * @author ：dyl
 * @date ：Created in 2019/10/7 12:08
 */
public interface ShopService extends IService<ShopDO> {

    /**
     * 更新店铺信息及图片
     * @param shopDO
     * @param img
     */
    void update(ShopDO shopDO, File img) throws Exception;
}
