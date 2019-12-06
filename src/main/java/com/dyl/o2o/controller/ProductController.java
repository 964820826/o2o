package com.dyl.o2o.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyl.o2o.common.R;
import com.dyl.o2o.domain.ProductDO;
import com.dyl.o2o.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** 商品相关控制层
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:07
 */
@RestController
@RequestMapping("/product")
@Api(tags = "商品")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/list")
    @ApiOperation("分页查询商品列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageIndex", value = "页码", required = true),
        @ApiImplicitParam(name = "pageSize", value = "每页显示数量",required = true)
    })
    public R getProductList(ProductDO productDO, int pageIndex, int pageSize){
        List<ProductDO> productDOList = productService.page(productDO, pageIndex, pageSize);

        return R.success(productDOList);
    }

}
