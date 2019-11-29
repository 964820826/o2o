package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.domain.ShopCategoryDO;
import com.dyl.o2o.service.ShopCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：dyl
 * @date ：Created in 2019/11/25 23:06
 */
@RequestMapping(value = "/shopCategory")
@RestController
@Api(tags = "店铺类别")
public class ShopCategoryController {

    @Autowired
    ShopCategoryService shopCategoryService;

    /**
     * 获取店铺类别列表
     * @param request
     * @return
     */
    @GetMapping("")
    @ApiOperation(value = "获取店铺列表", notes = "不传parentId代表查询一级店铺类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "上级店铺类别id")
    })
    public R getShopCategoryList(Long parentId, HttpServletRequest request){
        ShopCategoryDO shopCategoryDO = new ShopCategoryDO();
        shopCategoryDO.setParentId(parentId);
        List<ShopCategoryDO> shopCategoryDOList = shopCategoryService.selectShopCategoryList(shopCategoryDO);
        return R.success(shopCategoryDOList);
    }
}
