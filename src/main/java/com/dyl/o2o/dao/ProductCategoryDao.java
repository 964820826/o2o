package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.ProductCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 商品类别相关数据层
 *
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:30
 */
@Mapper
@Component
public interface ProductCategoryDao extends BaseMapper<ProductCategoryDO> {
}
