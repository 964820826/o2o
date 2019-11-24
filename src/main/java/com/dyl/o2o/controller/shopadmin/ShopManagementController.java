package com.dyl.o2o.controller.shopadmin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.domain.AreaDO;
import com.dyl.o2o.domain.PersonDO;
import com.dyl.o2o.domain.ShopCategoryDO;
import com.dyl.o2o.domain.ShopDO;
import com.dyl.o2o.service.AreaService;
import com.dyl.o2o.service.ShopCategoryService;
import com.dyl.o2o.service.ShopService;
import com.dyl.o2o.common.util.ImageUtil;
import com.dyl.o2o.common.util.PathUtil;
import com.dyl.o2o.common.util.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 店铺管理controller
 * @author ：dyl
 * @date ：Created in 2019/10/8 23:13
 */
@Slf4j
@RestController
@RequestMapping("/shopAdmin")
public class ShopManagementController {

    @Autowired
    ShopService shopService;
    @Autowired
    ShopCategoryService shopCategoryService;
    @Autowired
    AreaService areaService;

    /**
     * 对某个店铺进行管理操作
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/shopManagement/{id}")
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
     * 查询店铺列表
     * @return
     */
    @GetMapping(value = "/shop")
    private R getShopList(HttpServletRequest request){
        //todo 从会话中获取用户id
        //设置查询条件
        ShopDO shopDO = new ShopDO();
        shopDO.setOwnerId(1L);//此处硬编码，后续修改
        QueryWrapper queryWrapper = new QueryWrapper(shopDO);
        //调用service层查询
        List<ShopDO> shopDOList = shopService.list(queryWrapper);
//        IPage<ShopDO> page = shopService.page(new Page<ShopDO>(1,5),queryWrapper);
//        List<ShopDO> shopDOList = page.getRecords();
        return R.success(shopDOList);
    }
    /**
     * 注册店铺
     * @param request
     */
    @PostMapping(value = "/shop")
    private R registerShop(ShopDO shopDO, HttpServletRequest request){
        if (!CaptchaUtil.checkVerifyCode(request)){
            return R.error(ResultCode.CAPTCHA_FAIL);
        }
        //图片处理
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile uploadImg = multipartRequest.getFile("img");
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
                return R.error(ResultCode.INNER_ERROR);
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
    @PutMapping("/shop")
    public R updateShop(ShopDO shopDO, HttpServletRequest request) throws Exception {
        //校验验证码
        if (!CaptchaUtil.checkVerifyCode(request)){
            return R.error(ResultCode.CAPTCHA_FAIL);
        }
        //若有上传图片则更新图片
        if (request.getAttribute("img") != null){
            //从请求中获取图片
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            MultipartFile newImg = multipartHttpServletRequest.getFile("img");
            //保存图片
            String imgFileAbsolutePath = PathUtil.getImgBasePath() +"\\"+ ImageUtil.getRandomFileName() + ImageUtil.getFileNameExtension(newImg.getOriginalFilename());
            File img = new File(imgFileAbsolutePath);
            newImg.transferTo(img);

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
    @GetMapping("/shop/{id}")
    public String getShopById(HttpServletRequest request, @PathVariable("id") Long id){
        Map<String ,Object> map = new HashMap<>();
        ShopDO shopDO = shopService.getById(id);
        List<AreaDO> areaDOList = areaService.selectList();
        request.setAttribute("shopDO", shopDO);
        request.setAttribute("areaDOList", areaDOList);
        return "shop/modifyShop";
    }

    /**
     * 获取店铺创建初始化信息
     * @return
     */
    @GetMapping("/shopInitInfo")
    public R getShopInitInfo(HttpServletRequest request){
        Map<String,Object> map = new HashMap<String,Object>();
        List<ShopCategoryDO> shopCategoryDOList = new ArrayList<ShopCategoryDO>();
        List<AreaDO> areaDOList = new ArrayList<AreaDO>();
        try{
            //获取一级店铺类别和所有区域列表
            shopCategoryDOList = shopCategoryService.selectShopCategoryList(new ShopCategoryDO());
            areaDOList = areaService.selectList();
            map.put("shopCategoryDOList", shopCategoryDOList);
            map.put("areaDOList",areaDOList);
            return R.success(map);
        }catch(Exception e){
            return R.error(e.getMessage());
        }
    }

}
