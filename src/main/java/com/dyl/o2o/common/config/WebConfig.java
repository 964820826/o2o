package com.dyl.o2o.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.dyl.o2o.common.util.PathUtil.getImgBasePath;

/** 资源访问配置类
 * @author ：dyl
 * @date ：Created in 2019/12/22 22:18
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{

    /**
     * 图片资源访问配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imgBasePath = getImgBasePath();
        registry.addResourceHandler("/image/**").addResourceLocations(" file:" + imgBasePath + "/");
    }
}
