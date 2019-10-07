package com.dyl.o2o.controller.superadmin;

import com.dyl.o2o.domain.AreaDo;
import com.dyl.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 区域类的controller层
 * @author ：dyl
 * @date ：Created in 2019/9/7 14:13
 */
@RestController//@RestController=@Controller + @ResponseBody，返回Json格式不需要在方法前再加注解了
@RequestMapping("/superAdmin/area")
public class AreaController {

    @Autowired
    AreaService areaService;

    /**
     * 查询所有区域信息
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Map<String,Object> areaSelectList(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<AreaDo> areaDoList = areaService.selectList();
        try {
            areaDoList = areaService.selectList();
            modelMap.put("rows",areaDoList);
            modelMap.put("total",areaDoList.size());
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        return modelMap;
    }


}
