package com.dyl.o2o.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.common.util.CaptchaUtil;
import com.dyl.o2o.common.util.ImageUtil;
import com.dyl.o2o.domain.ProductDO;
import com.dyl.o2o.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 商品相关控制层
 *
 * @author ：dyl
 * @date ：Created in 2019/12/5 16:07
 */
@RestController
@RequestMapping("/product")
@Api(tags = "商品")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 分页查询商品列表
     *
     * @param productDO
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分页查询商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页码", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", required = true)
    })
    public R getProductList(ProductDO productDO, int pageIndex, int pageSize) {
        IPage<ProductDO> productDOList = productService.page(productDO, pageIndex, pageSize);
        return R.success(productDOList);
    }

    /**
     * 通过id获取指定商品详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("通过id获取商品详情")
    @ApiImplicitParam(name = "id", value = "商品id", required = true)
    public R getProductById(@PathVariable("id") Long id) {
        ProductDO productDO = productService.getById(id);
        return R.success(productDO);
    }

    @PutMapping("")
    @ApiOperation("修改店铺信息")
    public R modifyProduct(MultipartFile productImg, List<MultipartFile> productDetailImg,
                           ProductDO productDO, String captcha, HttpServletRequest request) throws IOException {
        //校验验证码
//        if (CaptchaUtil.checkVerifyCode(captcha,request.getSession())){
//            return R.error(ResultCode.CAPTCHA_FAIL);
//        }

        String productThumbnailPath;
        try{
            //判断是否需要更新商品缩略图片
            if (productImg != null) {
                productThumbnailPath =  ImageUtil.generateThumbnail(productImg);
                productDO.setProductThum(productThumbnailPath);

            }

            //判断是否需要更新商品详情图
            if (productDetailImg.size() != 0){
                for (MultipartFile file : productDetailImg) {

                }
            }
            //调用service层方法修改商品信息
            productService
        }

        //
    }
}
