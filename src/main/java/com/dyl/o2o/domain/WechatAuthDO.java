package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 微信授权实体类
 * @author ：dyl
 * @date ：Created in 2019/9/26 17:40
 */
@Data
@TableName("tb_wechat_auth")
public class WechatAuthDO {
    //微信授权id
    private Long wechatAtuhId;
    //绑定微信账号与公众号的唯一标识
    private String openId;
    //微信授权时间
    private Date createTime;
    //微信授权对应用户
    private PersonDO personDo;

}
