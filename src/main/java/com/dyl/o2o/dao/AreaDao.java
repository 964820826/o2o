package com.dyl.o2o.dao;

import com.dyl.o2o.domain.AreaDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/19 23:17
 */
@Mapper
public interface AreaDao {

    AreaDo allArea();
}
