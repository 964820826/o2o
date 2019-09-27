package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 本地账户(使用账号密码登陆的账号）
 * @author ：dyl
 * @date ：Created in 2019/9/26 17:45
 */
@Data
@TableName("tb_local_auth")
public class LocalAuthDo {
    //本地账户Id
    private Long localAuthId;
    //本地账户名
    private String localAuthName;
    //本地账户密码
    private String password;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date lastEditTime;
    //对应用户
    private PersonDo personDo;
}
