package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.domain.ProductDO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 商品相关控制层
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:07
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/list")
    @ApiOperation("分页查询商品列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "pageIndex", value = "页码"),
        @ApiImplicitParam(name = "pageSize",value = "每页显示数量")
    })
    public R getProductList(ProductDO productDO, int pageIndex, int pageSize){

        //todo 物理分页查询商品列表

        return R.success();
    }

}
