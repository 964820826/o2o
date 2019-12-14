package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.domain.AreaDO;
import com.dyl.o2o.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域类的controller层
 *
 * @author ：dyl
 * @date ：Created in 2019/9/7 14:13
 */
@RestController//@RestController=@Controller + @ResponseBody，返回Json格式不需要在方法前再加注解了
@RequestMapping("/area")
@Api(tags = "区域")
public class AreaController {

    @Autowired
    AreaService areaService;

    /**
     * 查询所有区域信息
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("获取所有区域信息")
    public R areaSelectList() {
        List<AreaDO> areaDoList = areaService.selectList();
        return R.success(areaDoList);
    }

}
