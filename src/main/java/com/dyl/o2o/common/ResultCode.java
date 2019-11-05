package com.dyl.o2o.common;

/** API统一返回状态码
 * @author ：dyl
 * @date ：Created in 2019/10/31 23:24
 */
public enum ResultCode {
    //成功
    SUCCESS(0,"成功"),

    //失败
    //参数原因
    CAPTCHA_FAIL(10001,"验证码错误"),
    PARAM_NOT_COMPLETE(10002,"参数缺失"),
    PARAM_TYPE_ERROR(10003,"参数类型错误"),

    //内部错误
    INNER_ERROR(50001,"内部错误");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态含义
     */
    private String massage;

    /**
     * 自定义状态码的构造函数
     * @param code
     * @param massage
     */
    ResultCode(Integer code, String massage){
        this.code = code;
        this.massage = massage;
    }

    /**
     * 获取此状态的状态码
     * @return
     */
    public Integer code(){
        return this.code;
    }

    /**
     * 获取此状态的含义
     * @return
     */
    public String massage(){
        return this.massage;
    }

    /**
     * 通过状态含义获取状态码
     * @param massage
     * @return
     */
    public static Integer getCode(String massage){
        for (ResultCode item : ResultCode.values()){
            if (item.massage().equals(massage)){
                return item.code;
            }
        }
        return null;
    }

    /**
     * 通过状态码获取状态含义
     * @param code
     * @return
     */
    public static String getMassage(Integer code){
        for (ResultCode item : ResultCode.values()){
            if (item.code().equals(code)){
                return item.massage;
            }
        }
        return null;
    }
}
