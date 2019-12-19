package com.dyl.o2o.pageJump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品相关页面的跳转
 *
 * @author ：dyl
 * @date ：Created in 2019/12/7 19:12
 */
@Controller
@RequestMapping("/product")
public class ProductJump {

    /**
     * 商品详情页跳转
     *
     * @return
     */
    @GetMapping("/detail")
    public String productDetail() {
        return "product/productDetail";
    }

    /**
     * 商品列表管理页跳转
     *
     * @return
     */
    @GetMapping("/listPage")
    public String productListPage() {
        return "/product/listProduct";
    }

    /**
     * 商品详情修改页跳转
     * @return
     */
    @GetMapping("/modify")
    public String productModifyPage(){
        return "/product/modifyProduct";
    }
}
