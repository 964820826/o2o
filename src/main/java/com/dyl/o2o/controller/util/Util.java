package com.dyl.o2o.controller.util;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "工具类")
public class Util {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/captchaImage")
    @ApiOperation(value = "生成验证码")
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

}
