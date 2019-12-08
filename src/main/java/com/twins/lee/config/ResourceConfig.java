package com.twins.lee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/**
 * @Authoe hanyun
 * @Email 1355081829@qq.com
 * @Date 2019/12/3
 **/
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Value("${twins.staticAccessPath}")
    private String staticAccessPath;
    @Value("${twins.uploadFolder}")
    private String uploadFolder;
    @Bean
    public MultipartConfigElement multipartConfigElement() {

        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.parse("5MB")); //KB,MB
        /// 设置总上传数据总大小
//        factory.setMaxRequestSize(DataSize.parse("300MB"));
        return factory.createMultipartConfig();
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

//        浏览上传的文件
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);

    }
}