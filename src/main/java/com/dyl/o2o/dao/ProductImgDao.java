package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.ProductImgDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/** 商品图片数据层
 * @author ：dyl
 * @date ：Created in 2019/12/7 18:49
 */
@Mapper
@Component
public interface ProductImgDao extends BaseMapper<ProductImgDO> {
}
