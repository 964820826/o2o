package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

/** 用户实体类（临时使用）todo 废除person类，使用user作为用户
 * @author ：dyl
 * @date ：Created in 2019/12/8 18:56
 */
@Data
@TableName("sys_user")
public class UserDO {
    //账户Id
    @TableId
    private Long userId;
    //账户名
    private String username;
    //密码
    private String password;
    //账户是否可用
    private boolean status;

    //用户角色
    @TableField(exist = false)
    private Set<RoleDO> roles;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date lastEditTime;
}
