package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.service.HeadLineService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** 首页controller
 * @author ：dyl
 * @date ：Created in 2019/11/29 17:00
 */
@Api(tags = "首页")
@RestController
public class IndexController {

    @Autowired
    HeadLineService headLineService;

    @GetMapping("/headLine")
    public R getHeadLineList(){
        headLineService.list();
        return R.success();
    }
}
