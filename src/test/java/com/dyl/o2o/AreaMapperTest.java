package com.dyl.o2o;

import com.dyl.o2o.domain.AreaDo;
import com.dyl.o2o.mapper.AreaMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
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
        List<AreaDo> areaDoList = areaMapper.selectList(null);
        for (AreaDo areaDo : areaDoList) {
            System.out.println(areaDo.toString());
        }
    }

    @Test
    public void insert(){
        System.out.println("测试开始!!");
        AreaDo areaDo = new AreaDo();
        areaDo.setAreaName("东区");
        int rows = areaMapper.insert(areaDo);
        System.out.println("影响记录数：" + rows);
    }
}
