package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/** 头条实体类
 * @author ：dyl
 * @date ：Created in 2019/9/27 20:16
 */
@Data
@TableName("tb_head_line")
@ApiModel("头条对象")
public class HeadLineDO {
    @ApiModelProperty(value = "头条id")
    private Long headLineId;

    @ApiModelProperty(value = "头条名称")
    private String headLineName;

    @ApiModelProperty(value = "头条指向链接")
    private String headLineLink;

    @ApiModelProperty(value = "头条图片地址")
    private String headLineImg;

    @ApiModelProperty(value = "权重越大，排名越靠前")
    private Integer priority;

    @ApiModelProperty(value = "可用状态")
    private Integer enableStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后修改时间")
    private Date lastEditTime;
}
