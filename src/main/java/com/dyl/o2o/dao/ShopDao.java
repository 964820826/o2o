package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.ShopDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 店铺操作的dao层
 * @author ：dyl
 * @date ：Created in 2019/10/3 11:40
 */
@Repository
@Mapper
public interface ShopDao extends BaseMapper<ShopDO> {

    List<ShopDO> selectList(@Param("shopDO") ShopDO shopDO);
}
