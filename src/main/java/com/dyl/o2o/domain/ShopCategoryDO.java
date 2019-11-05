package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 店铺类别实体类
 * @author ：dyl
 * @date ：Created in 2019/9/27 20:49
 */
@Data
@TableName("tb_shop_category")
public class ShopCategoryDO {
    //店铺类别Id
    private Long shopCategoryId;
    //店铺类别名称
    private String shopCategoryName;
    //店铺类别描述
    private String shopCategoryDesc;
    //图片
    private String shopCategoryImg;
    //权重，越大越靠前
    private Integer priority;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
    //上级id（自关联）
    private Long parentId;

}
