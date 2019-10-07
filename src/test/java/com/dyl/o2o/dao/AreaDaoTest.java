package com.dyl.o2o.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dyl.o2o.BaseTest;
import com.dyl.o2o.domain.AreaDo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 测试AreaMapper
 * @author ：dyl
 * @date ：Created in 2019/9/22 14:03
 */
public class AreaDaoTest extends BaseTest {

    @Autowired
    AreaDao areaDao;

    @Test
    public void testSelectList(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("priority");
        List<AreaDo> areaDoList = areaDao.selectList(queryWrapper);
        for (AreaDo areaDo : areaDoList) {
            System.out.println(areaDo.toString());
        }
    }

    @Test
    public void insert(){
        System.out.println("测试开始!!");
        AreaDo areaDo = new AreaDo();
        areaDo.setAreaName("西区");
        int rows = areaDao.insert(areaDo);
        System.out.println("影响记录数：" + rows);
    }
}
