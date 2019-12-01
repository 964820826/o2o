package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.dao.HeadLineDao;
import com.dyl.o2o.domain.HeadLineDO;
import com.dyl.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 头条业务层实现类
 *
 * @author ：dyl
 * @date ：Created in 2019/11/29 20:19
 */
@Service
public class HeadLineServiceImpl extends ServiceImpl<HeadLineDao, HeadLineDO> implements HeadLineService {

    @Autowired
    HeadLineDao headLineDao;

    /**
     * 获取头条列表
     *
     * @param headLineDO
     * @return
     */
    public List<HeadLineDO> list(HeadLineDO headLineDO) {
        QueryWrapper queryWrapper = new QueryWrapper(headLineDO);
        //以权重降序查询
        queryWrapper.orderByDesc("priority");
        List<HeadLineDO> headLineList = headLineDao.selectList(queryWrapper);
        return headLineList;
    }
}
