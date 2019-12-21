package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.ProductImgDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/** 商品图片数据层
 * @author ：dyl
 * @date ：Created in 2019/12/7 18:49
 */
@Mapper
@Component
public interface ProductImgDao extends BaseMapper<ProductImgDO> {

    /**
     * 通过商品id获取该店铺下的所有商品详情图片id（用于批量删除）
     * @param productId
     * @return
     */
    @Select("select product_img_id from tb_product_img where product_id = #{productId}")
    List<Long> selectImgIdListByProductId(Long productId);


    /**
     * 通过商品id获取该店铺下所有商品详情图片的路径
     * @param productId
     * @return
     */
    @Select("select product_img_addr from tb_product_img where product_id = #{productId}")
    List<String> selectImgPathListByProductId(Long productId);
}
