package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.HeadLineDO;
import org.apache.ibatis.annotations.Mapper;

/** 头条相关Dao层
 * @author ：dyl
 * @date ：Created in 2019/11/29 17:04
 */
@Mapper
public interface HeadLineDao extends BaseMapper<HeadLineDO> {
}
