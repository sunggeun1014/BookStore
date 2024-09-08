package com.ezen.bookstore.admin.commons;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    	registry.addResourceHandler("/images/banners/**")
                .addResourceLocations("file:///C:/images/banners/"); 
        
        registry.addResourceHandler("/images/books/**")
                .addResourceLocations("file:///C:/images/books/");   

        registry.addResourceHandler("/images/profiles/**")
                .addResourceLocations("file:///C:/images/profiles/");

        registry.addResourceHandler("/images/notice/**")
                .addResourceLocations("file:///C:/images/notice/");  
    }
}