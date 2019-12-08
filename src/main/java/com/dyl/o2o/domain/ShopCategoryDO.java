package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.jdbc.Null;

import java.util.Date;

/** 店铺类别实体类
 * @author ：dyl
 * @date ：Created in 2019/9/27 20:49
 */
@Data
@TableName("tb_shop_category")
@ApiModel(value = "shopCategory对象", description = "店铺类别对象")
public class ShopCategoryDO {
    @ApiModelProperty("店铺类别id")
    private Long shopCategoryId;

    @ApiModelProperty("店铺类别名称")
    private String shopCategoryName;

    @ApiModelProperty("店铺类别描述")
    private String shopCategoryDesc;

    @ApiModelProperty("店铺类别图片地址")
    private String shopCategoryImg;

    @ApiModelProperty("权重，越大越靠前")
    private Integer priority;

    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date lastEditTime;

    //上级id（自关联）
    @ApiModelProperty(value = "上级店铺类别id")
    private Long parentId;

}
