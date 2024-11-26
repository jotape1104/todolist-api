package com.karlos.todolist.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfiguration implements WebMvcConfigurer { // NOSONAR
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // NOSONAR
                .allowedOrigins("*") // NOSONAR
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT") // NOSONAR
                .allowedHeaders("*") // NOSONAR
                .allowCredentials(true); // NOSONAR
    }
}
