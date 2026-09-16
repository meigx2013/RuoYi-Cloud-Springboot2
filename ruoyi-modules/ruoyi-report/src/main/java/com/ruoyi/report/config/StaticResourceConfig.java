package com.ruoyi.report.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author meigx
 * @version 1.0
 * @description: 静态资源映射
 * @date 2026/2/1 23:36
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //关键配置：将 /static/jmreport/** 映射到 classpath:/static/jmreport/
        //registry.addResourceHandler("/static/jmreport/**")
        //        .addResourceLocations("classpath:/static/jmreport/");
    }

}
