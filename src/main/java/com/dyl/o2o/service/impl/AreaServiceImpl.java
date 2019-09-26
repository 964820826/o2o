package com.dyl.o2o.service.impl;

import com.dyl.o2o.domain.AreaDo;
import com.dyl.o2o.mapper.AreaMapper;
import com.dyl.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/19 23:15
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Override
    public List<AreaDo> selectList() {
        List<AreaDo> areaDoList = areaMapper.selectList(null);
        return areaDoList;
    }

}
