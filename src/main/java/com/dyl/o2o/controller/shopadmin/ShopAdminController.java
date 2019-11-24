package com.dyl.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** 店铺相关路由跳转controller
 * @author ：dyl
 * @date ：Created in 2019/10/11 11:06
 */
@Controller
@RequestMapping("/shopAdmin")
public class ShopAdminController {

//    /**
//     * 跳转到添加店铺页面
//     * @return
//     */
//    @GetMapping(value = "/shop")
//    public String addShop(){
//        return "addShop";
//    }

//    /**
//     * 跳转到修改店铺页面
//     * @param id
//     * @return
//     */
//    @GetMapping(value = "/modifyShop/{id}")
//    public String modifyShop(@PathVariable("id") Long id){
//        return "shop/modifyShop";
//    }
}
