package com.dyl.o2o.common.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/** 验证码配置
 * @author ：dyl
 * @date ：Created in 2019/11/2 17:06
 */
@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //配置参数里全部都是小写，出现大写会导致不生效
        //是否有边框
        properties.setProperty("kaptcha.border","yes");
        //边框颜色
        properties.setProperty("kaptcha.border.color","105,179,90");
        //图片宽度
        properties.setProperty("kaptcha.image.width","125");
        //图片高度
        properties.setProperty("kaptcha.image.height","45");
        //存放在session中的值
        properties.setProperty("kaptcha.session.key","code");
        //字体颜色
        properties.setProperty("kaptcha.textproducer.font.color","red");
        //字符集，验证码从其中获取字符
        properties.setProperty("kaptcha.textproducer.char.string","23456789");
        //字体个数
        properties.setProperty("kaptcha.textproducer.char.length","4");
        //字体样式
        properties.setProperty("kaptcha.textproducer.font.names","宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
