package com.sjprograming.restapi;

import org.springframework.context.annotation.Configuration;
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

        // Serve uploaded batch images
        registry
            .addResourceHandler("/uploads/**")
            .addResourceLocations("file:uploads/");
    }
}
