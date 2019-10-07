package com.dyl.o2o.core.exception;

import java.util.Date;
import java.util.HashMap;

/** 统一API结果响应封装
 * @author ：dyl
 * @date ：Created in 2019/10/3 13:38
 */
public class Result extends HashMap {
    private int code;
    private String massage;
    private Date date;
}
