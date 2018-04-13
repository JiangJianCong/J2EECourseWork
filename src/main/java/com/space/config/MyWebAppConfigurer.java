package com.space.config;

import com.space.Interceptor.EduInsInterceptor;
import com.space.Interceptor.ManagerInterceptor;
import com.space.Interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/userView/**");
        registry.addInterceptor(new EduInsInterceptor()).addPathPatterns("/eduInsView/**");
        registry.addInterceptor(new ManagerInterceptor()).addPathPatterns("/manager/**");
        super.addInterceptors(registry);
    }
}
