package com.dyl.o2o.controller;

import com.dyl.o2o.domain.AreaDo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/7 14:13
 */
@Controller//@RestController=@Controller + @ResponseBody，返回Json格式不需要在方法前再加注解了
@RequestMapping("/area")
public class AreaController {

    @RequestMapping("/helloWorld")
    public String getArea(Model model){
        AreaDo areaDo = new AreaDo();
        areaDo.setAreaName("北区");
        model.addAttribute("name","abcd");
        return "hello";
    }
}
