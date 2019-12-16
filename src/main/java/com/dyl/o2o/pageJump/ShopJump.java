package com.dyl.o2o.pageJump;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** 店铺相关路由跳转controller
 * @author ：dyl
 * @date ：Created in 2019/10/11 11:06
 */
@Controller
@RequestMapping("/shop")
public class ShopJump {

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

    /**
     * 跳转到店铺列表页面
     * @return
     */
    @GetMapping("/listShop")
    public String listShop(){
        return "shop/listShop";
    }

    /**
     * 跳转到店铺详情页
     * @return
     */
    @GetMapping("/detail")
    public String shopDetail(){
        return "shop/detail";
    }
}
