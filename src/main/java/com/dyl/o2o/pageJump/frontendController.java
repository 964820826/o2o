package com.dyl.o2o.pageJump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** 首页跳转
 * @author ：dyl
 * @date ：Created in 2019/11/29 16:28
 */
@Controller
public class frontendController {

    /**
     * 跳转到首页
     * @return
     */
    @GetMapping("/index")
    public String indexPage(){
        return "/index";
    }
}
