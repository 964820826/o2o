package com.dyl.o2o.service.impl;

import com.dyl.o2o.dao.AreaDao;
import com.dyl.o2o.domain.AreaDo;
import com.dyl.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/19 23:15
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Override
    public AreaDo allArea() {
        AreaDo areaDo = areaDao.allArea();
        return areaDo;
    }
}
