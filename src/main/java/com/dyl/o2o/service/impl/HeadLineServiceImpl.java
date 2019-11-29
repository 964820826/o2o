package com.dyl.o2o.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyl.o2o.dao.HeadLineDao;
import com.dyl.o2o.domain.HeadLineDO;
import com.dyl.o2o.service.HeadLineService;
import org.springframework.stereotype.Service;

/** 头条列表的service层实现类
 * @author ：dyl
 * @date ：Created in 2019/11/29 17:06
 */
@Service
public class HeadLineServiceImpl extends ServiceImpl<HeadLineDao, HeadLineDO> implements HeadLineService {
}
