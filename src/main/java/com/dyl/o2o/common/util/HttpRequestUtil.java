package com.dyl.o2o.common.util;

import javax.servlet.http.HttpServletRequest;

/** 从前端传来的http请求中获取参数（http请求中参数与后端接口需要的参数类型不同就返回-1）
 * @author ：dyl
 * @date ：Created in 2019/10/9 16:52
 */
public class HttpRequestUtil {
    /**
     * 从request里获取整型
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request,String key){
        try {
            return Integer.valueOf(request.getParameter(key));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 从request里获取字符串
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key){
        try{
            String result = request.getParameter(key);
            if (result != null){
                result = result.trim();
            }
            if ("".equals(result)){
                return null;
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
