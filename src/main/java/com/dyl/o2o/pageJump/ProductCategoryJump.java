package com.dyl.o2o.pageJump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：dyl
 * @date ：Created in 2019/12/24 15:19
 */
@Controller
@RequestMapping("/productCategory")
public class ProductCategoryJump {
    /**
     * 商品类别管理页跳转
     * @return
     */
    @GetMapping("/manage")
    public String productCategoryManagePage(){
        return "/productCategory/manageProductCategory";
    }
}
