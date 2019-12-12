package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/** 商品实体类
 * @author ：dyl
 * @date ：Created in 2019/9/28 16:47
 */
@Data
@TableName("tb_product")
@ApiModel("商品对象")
public class ProductDO {
    //商品id
    @ApiModelProperty("商品Id")
    @TableId
    private Long productId;

    //商品名称
    @ApiModelProperty("商品名称")
    private String productName;

    //商品描述
    @ApiModelProperty("商品描述")
    private String productDesc;

    //缩略图
    @ApiModelProperty("商品缩略图")
    private String productThum;

    //商品价格
    @ApiModelProperty("商品价格")
    private Float normalPrice;

    //折扣价格
    @ApiModelProperty("折扣价格")
    private Float discountPrice;

    //权重
    @ApiModelProperty("权重")
    private Integer priority;

    //可用状态  0下架  1前台展示
    @ApiModelProperty("商品可用状态")
    private Integer enableStatus;

    //创建时间
    @ApiModelProperty("创建时间")
    private Date createTime;

    //修改时间
    @ApiModelProperty("最后修改时间")
    private Date lastEditTime;

    //商品详情图片
    @TableField(exist = false)//数据库中不存在此字段
    @ApiModelProperty("商品详情图片")
    private List<ProductImgDO> productImgList;

    //商品所属类别
    @ApiModelProperty("商品所属类别id")
    private Long productCategoryId;

    //商品所属店铺
    @ApiModelProperty("商品所属店铺id")
    private Long shopId;
}
