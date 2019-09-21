package com.dyl.o2o.domain;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 区域实体类，对应数据库表(tb_area)
 *
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/15 16:25
 */
@Component
public class AreaDo {
    //区域id
    private Integer areaId;//为避免创建对象时默认赋值，故实体类中都使用包装类型
    //区域名称
    private String areaName;
    //区域权重（权重越大，排名越靠前）
    private Integer priority;
    //区域创建时间
    private Date createTime;
    //区域最后修改时间
    private Date lastEditTime;


    public Integer getAreaId() {
        return areaId;
    }

    public void setId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
