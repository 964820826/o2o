package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * 区域实体类
 *
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/15 16:25
 */
@Data
@TableName(value = "tb_area")
public class AreaDO {
    //区域id
    @TableId
    private Long areaId;//为避免创建对象时默认赋值，故实体类中都使用包装类型
    //区域名称
    private String areaName;
    //区域权重（权重越大，排名越靠前）
    private Integer priority;
    //区域创建时间
    private Date createTime;
    //区域最后修改时间
    private Date lastEditTime;
}
