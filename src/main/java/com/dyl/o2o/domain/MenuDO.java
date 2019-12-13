package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/** 权限实体类
 * @author ：dyl
 * @date ：Created in 2019/12/13 14:08
 */
@Data
public class MenuDO {

    //权限id
    @TableId
    private Long menuId;

    //权限名称
    private String name;

    //权限
    private String permission;
}
