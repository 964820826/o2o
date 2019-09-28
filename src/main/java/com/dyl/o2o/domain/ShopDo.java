package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 店铺实体类
 * @author ：dyl
 * @date ：Created in 2019/9/28 9:48
 */
@Data
@TableName("tb_shop")
public class ShopDo {
    //店铺id
    private Long shopId;
    //店铺名称
    private String shopName;
    //店铺描述
    private String shopDesc;
    //店铺图片
    private String shopImg;
    //权重
    private Integer priority;
    //地址
    private String shop_addr;
    //联系方式
    private String phone;
    //可用状态 -1:不可用 0:审核中 1:可用
    private Integer enableStatus;
    //建议（超管给店铺的建议）
    private String advice;
    //创建时间
    private Date createTime;
    //最后修改时间
    private Date lastEditTime;
    //店主
    private PersonDo owner;
    //区域
    private AreaDo area;
    //店铺类别
    private ShopCategoryDo shopCategory;
}
