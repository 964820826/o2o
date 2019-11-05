package com.dyl.o2o.controller.shopadmin;

import com.dyl.o2o.common.R;
import com.dyl.o2o.common.ResultCode;
import com.dyl.o2o.domain.AreaDO;
import com.dyl.o2o.domain.PersonDO;
import com.dyl.o2o.domain.ShopCategoryDO;
import com.dyl.o2o.domain.ShopDO;
import com.dyl.o2o.service.AreaService;
import com.dyl.o2o.service.ShopCategoryService;
import com.dyl.o2o.service.ShopService;
import com.dyl.o2o.util.ImageUtil;
import com.dyl.o2o.util.PathUtil;
import com.dyl.o2o.util.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 获取店铺创建初始化信息
     * @return
     */
    @RequestMapping("/shopInitInfo")
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
