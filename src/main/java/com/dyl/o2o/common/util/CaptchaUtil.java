package com.dyl.o2o.common.util;

import javax.servlet.http.HttpSession;

/**
 * @author ：dyl
 * @date ：Created in 2019/11/27 22:43
 */
public class CaptchaUtil {
    /**
     * 校验验证码是否正确
     * @param inputCaptcha 页面输入的验证码
     * @param session 会话；从会话中获取原始验证码
     * @return 验证通过返回true
     */
    public static boolean checkVerifyCode(String inputCaptcha, HttpSession session){
        //从会话中获取原始验证码，从请求中获取输入的验证码，忽略大小写比较，相同则返回true
        String originalCaptcha = (String) session.getAttribute("verify");
        if (originalCaptcha.equalsIgnoreCase(inputCaptcha)){
            return true;
        }else {
            return false;
        }
    }
}
