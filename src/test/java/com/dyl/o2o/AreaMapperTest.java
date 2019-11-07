package com.dyl.o2o;

import com.dyl.o2o.domain.AreaDO;
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
    public void testSelectList(){
        System.out.println("测试开始!!");
        List<AreaDO> areaDoList = areaMapper.selectList(null);
        for (AreaDO areaDo : areaDoList) {
            System.out.println(areaDo.toString());
        }
    }

    @Test
    public void insert(){
        System.out.println("测试开始!!");
        AreaDO areaDo = new AreaDO();
        areaDo.setAreaName("东区");
        int rows = areaMapper.insert(areaDo);
        System.out.println("影响记录数：" + rows);
    }
}
