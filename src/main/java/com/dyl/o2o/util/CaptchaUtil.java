package com.dyl.o2o.util;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/** 工具接口
 * @author ：dyl
 * @date ：Created in 2019/11/2 17:50
 */
@Controller
public class CaptchaUtil {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @GetMapping("/captchaImage")
    public void captchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try{
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            request.getSession().setAttribute("verify", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象，并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/jpeg");

        byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 校验验证码是否正确
     * @param request
     * @return
     */
    public static boolean checkVerifyCode(HttpServletRequest request){
        String originalCaptcha = (String) request.getSession().getAttribute("verify");
        String inputCaptcha = HttpRequestUtil.getString(request,"captcha");
        if (originalCaptcha.equalsIgnoreCase(inputCaptcha)){
            return true;
        }else {
            return false;
        }
    }
}
