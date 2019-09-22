package com.dyl.o2o;

import com.dyl.o2o.domain.AreaDo;
import com.dyl.o2o.mapper.AreaMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试AreaMapper
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/22 14:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaMapperTest {

    @Autowired
    AreaMapper areaMapper;

    @Test
    public void testAreaMapper(){
        System.out.println("Test begin!!");
        List<AreaDo> areaDoList = areaMapper.selectList(null);
        for (AreaDo areaDo : areaDoList) {
            System.out.println(areaDo.toString());
        }
    }
}
