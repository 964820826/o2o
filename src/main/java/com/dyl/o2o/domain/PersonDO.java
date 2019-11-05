package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 个人表，个人为系统使用的个体，可视为user，但为与常规理解登陆账户的user区分，使用person命名
 * @author ：dyl
 * @date ：Created in 2019/9/26 16:09
 */
@Data
@TableName("tb_person")
public class PersonDO {
    //用户Id
    private Long personId;
    //用户名
    private String personName;
    //头像地址
    private String profileImg;
    //邮箱
    private String email;
    //性别
    private String gender;
    //用户状态 禁用为0 启用为1
    private Integer enableStatus;
    //用户类型 1.顾客 2.店家 3.超管
    private Integer personType;
    //创建时间
    private Date createTime;
    //修改时间
    private Date lastEditTime;
}
