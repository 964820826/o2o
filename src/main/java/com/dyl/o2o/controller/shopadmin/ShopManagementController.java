package com.dyl.o2o.controller.shopadmin;

import com.dyl.o2o.core.exception.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/** 店铺管理controller
 * @author ：dyl
 * @date ：Created in 2019/10/8 23:13
 */
@RestController
@RequestMapping("/shopAdmin")
public class ShopManagementController {

    @RequestMapping(value = "/shop", method = RequestMethod.POST)
    private Result registerShop(HttpServletRequest request){

    }
}