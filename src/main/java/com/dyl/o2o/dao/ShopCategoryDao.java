package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.ShopCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/** 店铺类别dao层
 * @author ：dyl
 * @date ：Created in 2019/10/11 21:20
 */
@Repository
@Mapper
public interface ShopCategoryDao extends BaseMapper<ShopCategoryDO> {
}
