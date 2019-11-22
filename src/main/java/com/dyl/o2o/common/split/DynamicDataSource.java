package com.dyl.o2o.common.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/** 读写分离动态切换数据源
 * @author ：dyl
 * @date ：Created in 2019/11/11 22:38
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 通过key决定调用哪个数据源
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //依据不同需求返回不同的key
        return DynamicDataSourceHolder.getDBType();
    }
}
