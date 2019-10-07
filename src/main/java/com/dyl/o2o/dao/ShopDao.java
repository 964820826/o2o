package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.ShopDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/** 店铺操作的dao层
 * @author ：dyl
 * @date ：Created in 2019/10/3 11:40
 */
@Repository
@Mapper
public interface ShopDao extends BaseMapper<ShopDo> {

}
