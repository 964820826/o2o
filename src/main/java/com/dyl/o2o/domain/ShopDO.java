package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/** 店铺实体类
 * @author ：dyl
 * @date ：Created in 2019/9/28 9:48
 */
@Data
@TableName("tb_shop")
@ApiModel(value = "shop对象",description = "店铺对象")
public class ShopDO {
    @TableId(value = "shop_id", type = IdType.AUTO)
    @ApiModelProperty(value = "店铺id")
    private Long shopId;

    @ApiModelProperty(value = "店铺名")
    private String shopName;

    @ApiModelProperty(value = "店铺简介")
    private String shopDesc;

    @ApiModelProperty(hidden = true)
    private String shopImg;

    @ApiModelProperty(value = "权重（越大排名越靠前）")
    private Integer priority;

    @ApiModelProperty(value = "地址")
    private String shopAddr;

    @ApiModelProperty(value = "店铺联系电话")
    private String shopPhone;

    @ApiModelProperty(value = "可用状态 -1:不可用 0:审核中 1:可用")
    private Integer enableStatus;

    @ApiModelProperty(value = "建议（超管给店铺的建议）")
    private String advice;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后修改时间")
    private Date lastEditTime;

    @ApiModelProperty(value = "店主的personId")
    private Long ownerId;

    @ApiModelProperty(value = "店铺类别id")
    private Long shopCategoryId;

    @ApiModelProperty(value = "区域id")
    private Long areaId;
}
