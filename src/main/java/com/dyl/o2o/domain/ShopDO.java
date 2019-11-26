package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    //店铺id
    @TableId(value = "shop_id", type = IdType.AUTO)
    private Long shopId;

    //店铺名称
    @ApiModelProperty(example = "一点点奶茶")
    private String shopName;

    //店铺简介
    private String shopDesc;

    //店铺图片
    private String shopImg;

    //权重
    private Integer priority;

    //地址
    private String shopAddr;

    //联系方式
    private String shopPhone;

    //可用状态 -1:不可用 0:审核中 1:可用
    private Integer enableStatus;

    //建议（超管给店铺的建议）
    @ApiModelProperty(example = "建议")
    private String advice;

    //创建时间
    private Date createTime;

    //最后修改时间
    private Date lastEditTime;

    //归属店主的id
    @ApiModelProperty(example = "1")
    private Long ownerId;

    //所在区域的id
    @ApiModelProperty(example = "1")
    private Long shopCategoryId;

    //所在店铺类别的id
    @ApiModelProperty(example = "12")
    private Long areaId;
}
