package com.dyl.o2o.pageJump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页相关的跳转
 *
 * @author ：dyl
 * @date ：Created in 2019/11/29 20:21
 */
@Controller
public class IndexJump {

    @GetMapping("/index")
    public String indexPage() {
        return "/index";
    }

    @GetMapping("/loginPage")
    public String loginPage(){
        return "/login";
    }
}
