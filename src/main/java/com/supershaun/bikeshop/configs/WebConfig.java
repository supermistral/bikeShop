package com.supershaun.bikeshop.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${settings.mode}")
    private String mode;

    @Value("${settings.image.path}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        if (mode.equals("production")) {
            registry.addResourceHandler("/media/**")
                    .addResourceLocations("file://" + imagePath + "/");
        } else {
            registry.addResourceHandler("/media/**")
                    .addResourceLocations("classpath:/media/");
        }
    }
}
