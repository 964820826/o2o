package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.HeadLineDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/** 头条Dao层
 * @author ：dyl
 * @date ：Created in 2019/11/29 20:18
 */
@Mapper
@Component
public interface HeadLineDao extends BaseMapper<HeadLineDO> {
}
