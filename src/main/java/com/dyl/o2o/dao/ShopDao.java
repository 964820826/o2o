package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.ShopDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 店铺操作的dao层
 *
 * @author ：dyl
 * @date ：Created in 2019/10/3 11:40
 */
@Repository
@Mapper
public interface ShopDao extends BaseMapper<ShopDO> {

    /**
     * 查询店铺列表
     *
     * @param shopDO
     * @return
     */
    List<ShopDO> getShopList(@Param("shopDO") ShopDO shopDO);

    /**
     * 通过店铺拥有者的id获取店铺信息
     *
     * @param userId
     * @return
     */
    @Select("select * from tb_shop where owner_id = #{userId}")
    ShopDO getByOwnerId(Long userId);
}
