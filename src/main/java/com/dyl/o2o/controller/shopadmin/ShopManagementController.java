package com.dyl.o2o.controller.shopadmin;

import com.dyl.o2o.core.exception.Result;
import com.dyl.o2o.domain.ShopDo;
import com.dyl.o2o.util.HttpRequestUtil;
import com.dyl.o2o.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/** 店铺管理controller
 * @author ：dyl
 * @date ：Created in 2019/10/8 23:13
 */
@RestController
@RequestMapping("/shopAdmin")
public class ShopManagementController {

    @RequestMapping(value = "/shop", method = RequestMethod.POST)
    private R registerShop(HttpServletRequest request){
        //接收并转化参数，包括店铺信息和图片信息
        String shopStr = HttpRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        ShopDo shopDo = null;
        shopDo = mapper.readValue(shopStr, ShopDo.class);

    }
}
