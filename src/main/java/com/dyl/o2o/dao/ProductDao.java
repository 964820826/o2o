package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.ProductDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 商品相关的Dao层
 *
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:01
 */
@Mapper
@Component
public interface ProductDao extends BaseMapper<ProductDO> {
}
