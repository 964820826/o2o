package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/** 角色实体类
 * @author ：dyl
 * @date ：Created in 2019/12/13 10:12
 */
@Data
public class RoleDO {

    //角色id
    @TableId
    private Long roleId;

    //角色名称
    private String roleName;

}
