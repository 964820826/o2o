package com.dyl.o2o.service.impl;

import com.dyl.o2o.Dao.AreaDao;
import com.dyl.o2o.domain.AreaDo;
import com.dyl.o2o.service.AreaService;

/**
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/19 23:15
 */
public class AreaServiceImpl implements AreaService {

    private AreaDao areaDao;

    @Override
    public AreaDo getAreaById(int id) {
        AreaDo areaDo = areaDao.getAreaById(id);
        return areaDo;
    }
}
