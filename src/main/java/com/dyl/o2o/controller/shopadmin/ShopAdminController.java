package com.dyl.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 店铺相关路由跳转controller
 * @author ：dyl
 * @date ：Created in 2019/10/11 11:06
 */
@Controller
@RequestMapping("/shopAdmin")
public class ShopAdminController {

    @RequestMapping(value = "/shopOperate")
    public String shopOperation(){
        return "shop/shopOperation";
    }
}
