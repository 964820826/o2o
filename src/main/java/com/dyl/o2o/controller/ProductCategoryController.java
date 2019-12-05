package com.dyl.o2o.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyl.o2o.common.R;
import com.dyl.o2o.domain.ProductCategoryDO;
import com.dyl.o2o.domain.ProductDO;
import com.dyl.o2o.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品类别控制层
 *
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:26
 */
@RestController
@RequestMapping("/productCategory")
@Api(tags = "商品类别")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("/list")
    @ApiOperation("获取店铺类别列表")
    public R getProductCategoryList(ProductCategoryDO productCategoryDO) {
        List productCategoryList = productCategoryService.list(productCategoryDO);
        return R.success(productCategoryList);
    }
}
