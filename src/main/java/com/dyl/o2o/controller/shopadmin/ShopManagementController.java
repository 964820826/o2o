package com.dyl.o2o.controller.shopadmin;

import com.dyl.o2o.domain.AreaDo;
import com.dyl.o2o.domain.PersonDo;
import com.dyl.o2o.domain.ShopCategoryDo;
import com.dyl.o2o.domain.ShopDo;
import com.dyl.o2o.service.AreaService;
import com.dyl.o2o.service.ShopCategoryService;
import com.dyl.o2o.service.ShopService;
import com.dyl.o2o.util.HttpRequestUtil;
import com.dyl.o2o.util.ImageUtil;
import com.dyl.o2o.util.PathUtil;
import com.dyl.o2o.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

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
    private void registerShop(HttpServletRequest request) {

        //1.接收并转化参数，包括店铺信息和图片信息
        String shopStr = HttpRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        ShopDo shopDo = null;
        try {//将json字符串转为实体类
            shopDo = mapper.readValue(shopStr, ShopDo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //接收图片
        CommonsMultipartFile shopImg = null;
        //从会话的上下文获取图片
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());//todo 待优化，从网上找有没有更优解决办法
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;//将请求强转为多媒体请求
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else{//todo 若不具备文件流则报错，用参数校验来验证，统一异常拦截来处理异常

        }

        //2.注册店铺
        if(shopDo != null && shopImg != null){
            //从会话中获取操作人员
            PersonDo owner = new PersonDo();
            owner.setPersonId(1L);//todo 此处暂时使用固定代码，后面添加session操作，从session中获取
            shopDo.setOwnerId(owner.getPersonId());
            File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {//todo 异常处理
                e.printStackTrace();
            }
            try {
                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
            } catch (IOException e) {//todo
                e.printStackTrace();
            }
            try {
//                shopService.save(shopDo, shopImg);
            }catch(Exception e){
                throw new RuntimeException("业务异常" + e.getMessage());
            }
        }else{//todo 参数校验不通过抛异常

        }
    }

    private static void inputStreamToFile(InputStream inputStream, File file){
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,bytesRead);
            }
        }catch (Exception e){
            throw new RuntimeException("读取文件流出现异常：" + e.getMessage());
        }finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("关闭文件流发生异常：" + e.getMessage());
            }
        }
    }

    /**
     * 获取店铺创建初始化信息
     * @return
     */
    @RequestMapping("/shopInitInfo")
    public Map<String,Object> getShopInitInfo(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<ShopCategoryDo> shopCategoryDoList = new ArrayList<ShopCategoryDo>();
        List<AreaDo> areaDoList = new ArrayList<AreaDo>();
        try{
            shopCategoryDoList = shopCategoryService.selectShopCategoryList(new ShopCategoryDo());
            areaDoList = areaService.selectList();
        }catch(Exception e){
            throw new RuntimeException("获取初始化信息异常！" + e.getMessage());
        }
        modelMap.put("shopCategoryList", shopCategoryDoList);
        modelMap.put("areaList", areaDoList);
        return modelMap;
    }

}
