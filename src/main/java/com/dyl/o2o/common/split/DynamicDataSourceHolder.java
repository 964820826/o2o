package com.dyl.o2o.common.split;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程数据源
 * @author ：dyl
 * @date ：Created in 2019/11/11 22:49
 */
@Slf4j
public class DynamicDataSourceHolder {

    //用来保存key(ThreadLocal保证线程安全)
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    //设置主从数据源的key
    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE = "slave";

    /**
     * 获取线程的数据源类型
     * @return
     */
    public static String getDBType(){
        String db = contextHolder.get();
        //若没有设置数据源,使用主数据库中的数据
        if (db == null){
            db = DB_MASTER;
        }
        return db;
    }

    /**
     * 设置线程的数据源类型
     * @param str
     */
    public static void setDBType(String str){
        log.info("使用的数据源为:" + str);
        contextHolder.set(str);
    }


    /**
     * 清理连接类型
     */
    public static void clearDBType(){
        contextHolder.remove();
    }
}
