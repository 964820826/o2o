package com.dyl.o2o.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 结果响应类
 * @author ：dyl
 * @date ：Created in 2019/10/10 14:52
 */
@Data
@NoArgsConstructor
@ApiModel("结果包装对象")
public class R {

    @ApiModelProperty(value = "状态码，0为成功")
    private Integer code;

    @ApiModelProperty(value = "状态信息")
    private String massage;

    @ApiModelProperty(value = "返回的结果内容")
    private Object data;

    /**
     * 成功的方法
     * @return
     */
    public static R success(){
        R r = new R();
        r.setResultCode(ResultCode.SUCCESS);
        return r;
    }

    /**
     * 成功的方法（含数据）
     * @param data
     * @return
     */
    public static R success(Object data){
        R r = new R();
        r.setResultCode(ResultCode.SUCCESS);
        r.setData(data);
        return r;
    }

    /**
     * 失败的方法(失败信息自定义)
     * @param massage
     * @return
     */
    public static R error(String massage){
        R r = new R();
        r.setCode(10000);
        r.setMassage(massage);
        return r;
    }

    /**
     * 失败的方法
     * @param resultCode
     */
    public static R error(ResultCode resultCode) {
        R r = new R();
        r.setResultCode(resultCode);
        return r;
    }

    /**
     * 失败的方法（含数据）
     * @param resultCode
     */
    public static R error(ResultCode resultCode, Object data) {
        R r = new R();
        r.setResultCode(resultCode);
        r.setData(data);
        return r;
    }

    /**
     * 设置状态码和其对应含义
     * @param resultCode
     */
    public void setResultCode(ResultCode resultCode){
        this.code = resultCode.code();
        this.massage = resultCode.massage();
    }
}
