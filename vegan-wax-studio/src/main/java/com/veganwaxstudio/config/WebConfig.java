package com.veganwaxstudio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.veganwaxstudio.interceptor.AutorizacionInterceptor;

// Configuracion de Spring MVC para registrar el interceptor de autorizacion de usuarios.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AutorizacionInterceptor autorizacionInterceptor;

    public WebConfig(AutorizacionInterceptor autorizacionInterceptor) {
        this.autorizacionInterceptor = autorizacionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(autorizacionInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/auth/login",
                        "/api/auth/logout");
    }
}