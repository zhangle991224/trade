package com.dm.trade.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
class WebConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    BootdoConfig bootdoConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = bootdoConfig.getUploadPath();
        String otherPath = bootdoConfig.getOthersPath();
        String property = System.getProperty("customer.dir") + "/";
        // 相对路径
        if (!uploadPath.startsWith("/")) {
            uploadPath = property + uploadPath;
        }
        if (!otherPath.startsWith("/")) {
            otherPath = property + otherPath;
        }
//        registry.addResourceHandler("/files/**").addResourceLocations("file:" + uploadPath);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/soft/**").addResourceLocations("file:" + otherPath);
    }

}