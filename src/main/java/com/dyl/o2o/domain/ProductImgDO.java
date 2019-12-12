package com.dyl.o2o.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/** 商品图片实体类
 * @author ：dyl
 * @date ：Created in 2019/9/28 16:40
 */
@Data
@TableName("tb_product_img")
public class ProductImgDO {
    //图片id
    @TableId
    private Long productImgId;
    //图片地址
    private String productImgAddr;
    //图片描述
    private String productImgDesc;
    //权重
    private Integer priority;
    //创建时间
    private Date createTime;
    //所属商品的id
    private Long productId;
}
