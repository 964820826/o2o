package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyl.o2o.domain.AreaDO;
import com.dyl.o2o.dao.AreaDao;
import com.dyl.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域service层的实现类
 *
 * @author ：dyl
 * @date ：Created in 2019/9/19 23:15
 */
@Service //指代service层，将此类实例化，需标注到实现类上
public class AreaServiceImpl implements AreaService {

    @Autowired //自动装配，不必通过new的方式创建对象
    private AreaDao areaDao;

    @Override
    public List<AreaDO> selectList() {
        //权重降序查询所有区域信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("priority");
        List<AreaDO> areaDOList = areaDao.selectList(queryWrapper);
        return areaDOList;
    }

}
