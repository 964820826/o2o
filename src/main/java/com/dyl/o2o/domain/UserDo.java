package com.dyl.o2o.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author ：dyl
 * @date ：Created in 2019/9/26 16:09
 */
@Data
public class UserDo {
    //用户Id
    private Long userId;
    //用户名
    private String userName;
    //头像地址
    private String profileImg;
    //邮箱
    private String email;
    //性别
    private String gender;
    //用户状态 禁用为0 启用为1
    private Integer enableStatus;
    //用户类型 1.顾客 2.店家 3.超管
    private Integer userType;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
}
