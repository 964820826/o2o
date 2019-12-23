package com.dyl.o2o.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.common.util.CaptchaUtil;
import com.dyl.o2o.common.util.ImageUtil;
import com.dyl.o2o.domain.ProductDO;
import com.dyl.o2o.service.ProductImgService;
import com.dyl.o2o.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    ProductImgService productImgService;

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

    /**
     * 修改商品信息
     * @param productImg
     * @param productDetailImgList
     * @param productDO
     * @param captcha
     * @param request
     * @return
     * @throws IOException
     */
    @PutMapping("")
    @ApiOperation("修改商品信息")
    @PreAuthorize("hasAnyAuthority('admin','product_modify')")
    public R modifyProduct(MultipartFile productImg, List<MultipartFile> productDetailImgList,
                           ProductDO productDO, String captcha, HttpServletRequest request) throws IOException {
        //1.校验验证码
        if (!CaptchaUtil.checkVerifyCode(captcha,request.getSession())){
            return R.error(ResultCode.CAPTCHA_FAIL);
        }

        //2.将用户上传的图片加工为缩略图保存到服务器上（若没有上传，获取到的路径为null）
        String productThumbnailPath = ImageUtil.uploadThumbnail(productImg);
        List<String> productDetailImgPathList = ImageUtil.uploadFileList(productDetailImgList);

        //3.调用service层方法，将商品和图片信息更新到数据库
        try {
            productService.updateDetail(productThumbnailPath, productDetailImgPathList, productDO);
        } catch (Exception e) {
            //4.更新商品信息时出现异常则将之前上传的文件删除
            ImageUtil.deleteFile(productThumbnailPath);
            ImageUtil.deleteFileList(productDetailImgPathList);
            throw e;
        }
        //5.返回程序运行结果
        return R.success();
    }

    @DeleteMapping("")
    @ApiOperation("删除商品信息")
    @ApiImplicitParam(value = "商品Id")
    @PreAuthorize("hasAnyAuthority('admin','product_delete')")
    public R deleteProduct(Long productId){
        productService.remove(productId);
        return R.success();
    }

    @PostMapping("")
    @ApiOperation("添加商品信息")
    @PreAuthorize("hasAnyAuthority('admin','product_add')")
    public R addProduct(MultipartFile productImg, List<MultipartFile> productDetailImgList, ProductDO productDO, String captcha, HttpServletRequest request) throws IOException {
        //1.校验验证码
//        if (!CaptchaUtil.checkVerifyCode(captcha,request.getSession())){
//            return R.error(ResultCode.CAPTCHA_FAIL);
//        }

        //2.将用户上传的图片保存到本地
        String productThumPath = ImageUtil.uploadThumbnail(productImg);
        List<String> productDetailImgPathList = ImageUtil.uploadFileList(productDetailImgList);

        //3.调用service方法，新增数据到数据库
        productDO.setProductThum(productThumPath);
        productService.save(productDetailImgPathList,productDO);

        //4.返回结果
        return R.success();
    }
}
