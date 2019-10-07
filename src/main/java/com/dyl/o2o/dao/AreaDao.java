package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.AreaDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 区域相关的dao层
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/19 23:17
 */
@Repository
@Mapper
public interface AreaDao extends BaseMapper<AreaDo> {//继承BaseMapper为了避免重复写基础的crud

}
