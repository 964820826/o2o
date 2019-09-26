package com.dyl.o2o.domain;

import lombok.Data;

/** 本地账户（tb_local_auth)
 * @author ：dyl
 * @date ：Created in 2019/9/26 17:45
 */
@Data
public class LocalAuth {
    //本地账户Id
    private Long localAuthId;
    //本地账户名
    private String localAuthName;
    //本地账户密码
    private String password;
}
