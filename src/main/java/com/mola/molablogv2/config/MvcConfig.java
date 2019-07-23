package com.mola.molablogv2.config;

import com.mola.molablogv2.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.Arrays;

/**
 * @Author: molamola
 * @Date: 19-6-19 下午8:59
 * @Version 1.0
 */
@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer{

    @Value("${my-conf.rootPath}")
    private String rootPath;

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        //不拦截静态资源和登录界面
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList("/statics/**","/","/validate/**","/user/login","/client/**","/page/**"));
    }

    /**
     * 资源定位
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/image/**")
                .addResourceLocations("file:" + rootPath + File.separator + "/upload/image/");
    }

    /**
     * view映射
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //登录页面
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/blogmanager").setViewName("blogmanager");
    }
}
