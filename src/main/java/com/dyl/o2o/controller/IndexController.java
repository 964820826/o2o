package com.dyl.o2o.controller;

import com.dyl.o2o.common.R;
import com.dyl.o2o.domain.HeadLineDO;
import com.dyl.o2o.service.HeadLineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 首页相关的控制层
 *
 * @author ：dyl
 * @date ：Created in 2019/11/29 20:23
 */
@Api(tags = "首页相关")
@RestController
public class IndexController {

    @Autowired
    HeadLineService headLineService;

    /**
     * 获取头条列表
     *
     * @param headLineDO
     * @return
     */
    @GetMapping("/headLine")
    @ApiOperation(value = "获取头条列表", notes = "需要以哪个字段查询填写哪个字段")
    public R queryHeadLine(HeadLineDO headLineDO) {
        List<HeadLineDO> headLineDOList = headLineService.list(headLineDO);
        return R.success(headLineDOList);
    }
}
