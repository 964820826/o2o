package com.dyl.o2o.common.util.security;

import com.dyl.o2o.domain.UserDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/** 用户安全实体
 * @author ：dyl
 * @date ：Created in 2019/12/8 18:55
 */
@Data
public class JWTUser implements UserDetails {

    //账户Id
    private Long userId;
    //账户名
    private String username;
    //密码
    private String password;
    //账户是否可用
    private String status;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date lastEditTime;

    //用户角色
    private Collection<GrantedAuthority> authorities;
    //是否过期
    private boolean isAccountNonExpired = false;
    //账户是否被锁定
    private boolean isAccountNonLocked = false;
    //证书是否过期
    private boolean isCredentialsNonExpired = false;
    //账户是否有效
    private boolean isEnabled = true;

    public JWTUser(UserDO userDO){
        BeanUtils.copyProperties(userDO,this);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
