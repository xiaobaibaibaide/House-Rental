package com.house.rent.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/housepic/**").addResourceLocations("file:/D:/bs program1/housepic/");
        registry.addResourceHandler("/avator/**").addResourceLocations("file:/D:/bs program1/avator/");
        registry.addResourceHandler("/actpic/**").addResourceLocations("file:/D:/bs program1/actpic/");
    }

}