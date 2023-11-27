package com.mju.management.global.config.jwtInterceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtInterceptor jwtInterceptor;
    private final Environment environment;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(!"test".equals(environment.getActiveProfiles()[0]))
            registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**");
    }
}