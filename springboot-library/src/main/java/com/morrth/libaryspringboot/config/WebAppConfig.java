package com.morrth.libaryspringboot.config;

/**
 * @author morrth
 * @create 2021-04-22-18:23
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.constraints.Max;

/**
 * 注册拦截器
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns("/voices/upload","/login","/register","/nllogin"); //设置不拦截的请求地址
    }

}
