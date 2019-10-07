package com.dyl.o2o.service;

import com.dyl.o2o.BaseTest;
import com.dyl.o2o.domain.AreaDo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/** 区域service的测试类
 * @author ：dyl
 * @date ：Created in 2019/10/2 14:21
 */
public class AreaServiceTest extends BaseTest {
    @Autowired
    AreaService areaService;

    @Test
    public void testListArea(){
        List<AreaDo> areaDoList = areaService.selectList();
        System.out.println("共 " + areaDoList.size() + " 条记录！");
        for (AreaDo area : areaDoList){
            System.out.println(area.toString());
        }
    }
}
