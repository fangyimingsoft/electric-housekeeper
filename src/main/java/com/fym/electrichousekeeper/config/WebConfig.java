package com.fym.electrichousekeeper.config;

import com.fym.electrichousekeeper.config.interceptors.GlobalInterceptor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebConfig implements WebMvcConfigurer {

    @Value("${config.login.required}")
    private Boolean requireLogin;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        GlobalInterceptor globalInterceptor = new GlobalInterceptor();
        globalInterceptor.setRequireLogin(requireLogin);
        registry.addInterceptor(globalInterceptor);
    }
}
