package com.dyl.o2o.util;

import lombok.Data;

import static org.springframework.http.HttpStatus.OK;

/** 结果响应类
 * @author ：dyl
 * @date ：Created in 2019/10/10 14:52
 */
@Data
public class R {
    //状态码
    private int status;

    //状态信息
    private String massage;

    //成功返回的结果
    private Object data;

    /**
     * 成功的方法
     * @param obj
     * @return
     */
    public static R ok(Object obj){
        R r = new R();
        r.setStatus(OK.value());
        r.setMassage("操作成功");
        r.setData(obj);
        return r;
    }

    /**
     * 失败的方法
     * @param value
     */
    public static R error(int value, String msg) {
        R r = new R();
        r.setStatus(value);
        r.setMassage(msg);
        return r;
    }
}
