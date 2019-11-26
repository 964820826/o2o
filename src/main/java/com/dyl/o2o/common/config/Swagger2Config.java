package com.dyl.o2o.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * 访问地址：http://项目地址/swagger-ui.html
 *
 * @author ：dyl
 * @date ：Created in 2019/11/25 22:44
 */
@Configuration//配置类的注解
@EnableSwagger2//启动Swagger2
public class Swagger2Config {

    /**
     * 创建Api应用
     * apiInfo()增加API相关信息
     * @return
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dyl.o2o.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息（将会展现到文档页面中）
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("RESTful APIs")
                .description("练习项目，好好钻研技术呀！")
                .termsOfServiceUrl("o2o")
                .contact("dyl")
                .version("1.0")
                .build();
    }
}
