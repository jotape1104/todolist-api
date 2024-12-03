package com.karlos.todolist.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfiguration implements WebMvcConfigurer { // sonar-ignore
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // sonar-ignore-begin
        registry.addMapping("/**") 
                .allowedOrigins("*") 
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT") 
                .allowedHeaders("*") 
                .allowCredentials(true); 
    }
    // sonar-ignore-end
}
