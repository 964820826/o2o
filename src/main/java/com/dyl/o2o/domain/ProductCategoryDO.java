package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 商品类别实体类
 * @author ：dyl
 * @date ：Created in 2019/9/28 15:43
 */
@Data
@TableName("tb_product_category")
public class ProductCategoryDO {
    //商品类别id
    private Long productCategoryId;
    //商品类别名称
    private String productCagtegoryName;
    //商品类别权重
    private Integer priority;
    //类别所在店铺
    private Long shopId;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
}
