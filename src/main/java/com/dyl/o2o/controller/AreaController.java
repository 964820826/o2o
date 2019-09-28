package com.dyl.o2o.controller;

import com.dyl.o2o.domain.AreaDo;
import com.dyl.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/7 14:13
 */
@RestController//@RestController=@Controller + @ResponseBody，返回Json格式不需要在方法前再加注解了
@RequestMapping("/area")
public class AreaController {

    @Autowired
    AreaService areaService;

    @RequestMapping("/selectList")
    public String areaSelectList(Model model){
        List<AreaDo> areaDo = areaService.selectList();
        return areaDo.toString();
    }

    @RequestMapping("/getArea")
    public String getArea(Model model){
        List<AreaDo> areaDo = areaService.selectList();
        return areaDo.toString();
    }
}
