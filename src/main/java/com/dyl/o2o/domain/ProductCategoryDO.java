package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/** 商品类别实体类
 * @author ：dyl
 * @date ：Created in 2019/9/28 15:43
 */
@Data
@TableName("tb_product_category")
@ApiModel("商品类别")
public class ProductCategoryDO {
    //商品类别id
    @TableId
    @ApiModelProperty("商品类别Id")
    private Long productCategoryId;

    //商品类别名称
    @ApiModelProperty("商品类别名称")
    private String productCategoryName;

    //商品类别权重
    @ApiModelProperty("权重")
    private Integer priority;

    //类别所在店铺
    @ApiModelProperty("类别所在店铺")
    private Long shopId;

    //创建时间
    @ApiModelProperty("创建时间")
    private Date createTime;

    //修改时间
    @ApiModelProperty("最后修改时间")
    private Date lastEditTime;
}
