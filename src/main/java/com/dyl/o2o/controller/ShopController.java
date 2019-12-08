package com.dyl.o2o.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.common.util.CaptchaUtil;
import com.dyl.o2o.common.util.PageUtil;
import com.dyl.o2o.domain.PersonDO;
import com.dyl.o2o.domain.ShopDO;
import com.dyl.o2o.service.AreaService;
import com.dyl.o2o.service.ShopCategoryService;
import com.dyl.o2o.service.ShopService;
import com.dyl.o2o.common.util.ImageUtil;
import com.dyl.o2o.common.util.PathUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

/** 店铺管理controller
 * @author ：dyl
 * @date ：Created in 2019/10/8 23:13
 */
@Slf4j
@RestController
@RequestMapping("/shop")
@Api(tags = "店铺")
public class ShopController {

    @Autowired
    ShopService shopService;
    @Autowired
    ShopCategoryService shopCategoryService;
    @Autowired
    AreaService areaService;

    /**
     * 根据获取店铺信息
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/manage/{id}")
    private R shopManage(@PathVariable Long id, HttpServletRequest request){
        //todo 从session中获取用户信息，只能管理该用户名下的店铺
        PersonDO personDO = (PersonDO) request.getSession().getAttribute("user");
        Long ownerId = personDO.getPersonId();

        ShopDO shopDO = shopService.getById(id);
        if (shopDO.getOwnerId() != ownerId){
            //操作的店铺不是当前用户名下的，无操作权限
            return R.error(ResultCode.NO_AUTHORITY);
        }
        //将通过校验的店铺id设置到请求中，用于生成前端按钮的链接
        request.setAttribute("shopId",id);
        return R.success();
    }

    /**
     * 分页查询店铺列表
     * @param shopDO
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "分页查询店铺列表",notes = "若输入店铺类别为一级类别的id，则获取该一类下的所有店铺")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页显示数量",required = true)
    })
    private R getShopListPage(ShopDO shopDO, int pageIndex, int pageSize, HttpServletRequest request){
        //从session中获取店铺列表
        List<ShopDO> shopDOList = (List<ShopDO>) request.getSession().getAttribute("shopDOList");
        ShopDO oriShop = (ShopDO) request.getSession().getAttribute("oriShop");
        if (oriShop == null){
            oriShop = new ShopDO();
        }
        //若无缓存数据或输入条件与原先不同
        if (shopDOList == null || !shopDO.equals(oriShop)){
            //从数据库查询
            shopDOList = shopService.list(shopDO);
            if (shopDOList == null){
                return R.success();
            }
            request.getSession().setAttribute("oriShop",shopDO);
            request.getSession().setAttribute("shopDOList",shopDOList);
        }
        //根据分页信息获取数据返回给前端
        List<ShopDO> shopPageList = PageUtil.toPage(pageIndex,pageSize,shopDOList);
        Page<ShopDO> pageShop = new Page<>(pageIndex,pageSize,shopDOList.size());
        pageShop.setRecords(shopPageList);
        return R.success(pageShop);
    }

    /**
     * 注册店铺
     * @param request
     */
    @ApiOperation(value = "注册店铺")
    @PostMapping(value="")
    @ApiImplicitParams({//name与入参中的参数名对应,value代表输入框上的提示
            @ApiImplicitParam(name = "inputCaptcha", value = "验证码",required = true),
    })
    private R registerShop(@ApiParam(value = "店铺缩略图") MultipartFile uploadImg, String inputCaptcha, ShopDO shopDO, HttpServletRequest request){
        if (!CaptchaUtil.checkVerifyCode(inputCaptcha,request.getSession())){
            return R.error(ResultCode.CAPTCHA_FAIL);
        }
        if (shopDO != null &&uploadImg != null){
            String imgFileAbsolutePath = PathUtil.getImgBasePath() +"\\"+ ImageUtil.getRandomFileName() + ImageUtil.getFileNameExtension(uploadImg.getOriginalFilename());
            File imgFile = new File(imgFileAbsolutePath);
            try {
                //MultipartFile转File,若原文件存在则会覆盖原文件,不存在则创建
                uploadImg.transferTo(imgFile);
                //生成缩略图替换原文件
                ImageUtil.generateThumbnail(imgFile);

                //从会话中获取操作人员
                PersonDO owner = new PersonDO();
                owner.setPersonId(1L);//todo 此处暂时使用固定代码，后面添加session操作，从session中获取
                shopDO.setOwnerId(owner.getPersonId());
                shopDO.setShopImg(imgFile.getName());
                shopService.save(shopDO);
            }catch (Exception e) {
                imgFile.delete();
                log.error("保存店铺信息异常:" + e.getMessage());
                e.printStackTrace();
                return R.error(ResultCode.SERVICE_ERROR);
            }
        }else{
            return R.error(ResultCode.PARAM_NOT_COMPLETE);
        }
        log.info("添加店铺成功");
        return R.success();
    }

    /**
     * 更新店铺
     * @param shopDO
     * @param request
     * @return
     * @throws Exception
     */
    //todo:参数校验，入参id不可为空
    //todo:获取登陆的用户，只能展示该用户下的店铺，若修改其他店铺则报错
    @PutMapping("")
    @ApiOperation(value = "修改店铺")
    public R updateShop(@ApiParam(value = "新图片") MultipartFile newImg, ShopDO shopDO, String captcha, HttpServletRequest request) throws Exception {
        //校验验证码
        if (!CaptchaUtil.checkVerifyCode(captcha, request.getSession())){
            return R.error(ResultCode.CAPTCHA_FAIL);
        }
        //若有上传图片则更新图片
        if (request.getAttribute("img") != null){
            //从请求中获取图片
//            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
//            MultipartFile newImg = multipartHttpServletRequest.getFile("img");
            //保存图片
            String imgFileAbsolutePath = PathUtil.getImgBasePath() +"\\"+ ImageUtil.getRandomFileName() + ImageUtil.getFileNameExtension(newImg.getOriginalFilename());
            File img = new File(imgFileAbsolutePath);
            if (!img.getParentFile().exists()){
                img.getParentFile().mkdirs();
            }
            newImg.transferTo(img);
            ImageUtil.generateThumbnail(img);

            //更新店铺信息及图片
            shopService.update(shopDO,img);
        }else {
            shopService.updateById(shopDO);
        }
        return R.success();
    }

    /**
     * 根据id获取店铺信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id获取店铺")
    @ApiImplicitParam("店铺id")
    public R getShopById(@PathVariable("id") Long id){
        ShopDO shopDO = shopService.getById(id);
        return R.success(shopDO);
    }

    /**
     * 获取当前用户名下的店铺
     * @param request
     * @return
     */
    @GetMapping("/currentUser")
    public R getCurrentUserShop(HttpServletRequest request){
        //从session中获取当前登陆用户personId
        Long personId = 1L;
        ShopDO queryCondition = new ShopDO();
        queryCondition.setOwnerId(personId);
        ShopDO shopDO = shopService.getOne(new QueryWrapper<>(queryCondition));
        return R.success(shopDO);
    }
}
