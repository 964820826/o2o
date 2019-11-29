package com.dyl.o2o.pageJump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** 店铺相关路由跳转controller
 * @author ：dyl
 * @date ：Created in 2019/10/11 11:06
 */
@Controller
@RequestMapping("/shop")
public class ShopAdminController {

    /**
     * 跳转到添加店铺页面
     * @return
     */
    @GetMapping(value = "/add")
    public String addShop(){
        return "shop/addShop";
    }

    /**
     * 跳转到修改店铺页面
     * @return
     */
    @GetMapping(value = "/modify")
    public String modifyShop(){
        return "shop/modifyShop";
    }

    /**
     * 跳转到管理店铺界面
     * @return
     */
    @GetMapping(value = "/manage")
    public String manageShop(){
        return "shop/manageShop";
    }
}
