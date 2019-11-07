package com.dyl.o2o.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyl.o2o.domain.AreaDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author ：dyl
 * @description：
 * @date ：Created in 2019/9/19 23:17
 */
@Repository
@Mapper
public interface AreaMapper extends BaseMapper<AreaDO> {//继承BaseMapper为了避免重复写基础的crud

}
