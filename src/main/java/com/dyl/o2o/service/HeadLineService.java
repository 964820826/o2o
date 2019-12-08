package com.dyl.o2o.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyl.o2o.domain.HeadLineDO;

import java.util.List;

/**
 * 头条相关业务层接口
 *
 * @author ：dyl
 * @date ：Created in 2019/11/29 20:18
 */
public interface HeadLineService extends IService<HeadLineDO> {

    /**
     * 获取头条列表
     *
     * @param headLineDO
     * @return
     */
    List<HeadLineDO> list(HeadLineDO headLineDO);
}
