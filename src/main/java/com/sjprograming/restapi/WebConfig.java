package com.sjprograming.restapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Serve static HTML, CSS, JS
        registry
            .addResourceHandler("/**")
            .addResourceLocations("classpath:/static/");

        // Serve uploaded images/files
        registry
            .addResourceHandler("/uploads/**")
            .addResourceLocations("file:uploads/");
    }

    // ✅ IMPORTANT: Enable CORS for HTML → API calls
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/api/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*");
    }
}
