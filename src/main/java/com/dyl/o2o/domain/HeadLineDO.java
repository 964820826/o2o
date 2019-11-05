package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 头条实体类
 * @author ：dyl
 * @date ：Created in 2019/9/27 20:16
 */
@Data
@TableName("tb_head_line")
public class HeadLineDO {
    //头条id
    private Long headLineId;
    //头条名称
    private String headLineName;
    //头条指向链接
    private String headLineLink;
    //头条图片地址
    private String headLineImg;
    //权重
    private Integer priority;
    //可用状态
    private Integer enableStatus;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date lastEditTime;
}
