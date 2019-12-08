package com.dyl.o2o.common.util;

import java.util.List;

/**
 * 分页相关工具类
 *
 * @author ：dyl
 * @date ：Created in 2019/12/6 22:19
 */
public class PageUtil {

    /**
     * 根据页码和每页行数转换为查询数据库的起始行数
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int pageIndexToBeginRows(int pageIndex, int pageSize) {
        return (pageIndex - 1) * pageSize;
    }

    /**
     * 逻辑分页，从包含完整信息的list中取出指定页显示的sublist
     * @param pageIndex
     * @param pageSize
     * @param list
     * @return
     */
    public static List toPage(int pageIndex, int pageSize, List list){
        int beginIndex = (pageIndex-1)*pageSize;
        int endIndex = pageIndex*pageSize;
        if (list.size() < endIndex){
            endIndex = list.size();
        }
        List subList = list.subList(beginIndex,endIndex);
        return subList;
    }
}
