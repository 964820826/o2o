package com.dyl.o2o.pageJump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：dyl
 * @date ：Created in 2019/12/7 19:12
 */
@Controller
@RequestMapping("/product")
public class ProductJump {

    @GetMapping("/detail")
    public String productDetail(){
        return "product/productDetail";
    }
}
