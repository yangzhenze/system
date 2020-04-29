package com.swsk.web.config;
import com.swsk.web.config.bean.ConfigResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zzy
 * @Date 2019-07-19 16:28
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    ConfigResource configResource;

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    /**
     * 添加静态资源文件，外部可以直接访问地址
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        configResource.getResources().forEach( r->{
            String [] paths = r.getPathPatterns().split(",|，");
            String [] resources = r.getResourceLocations().split(",|，");
            registry.addResourceHandler(paths).addResourceLocations(resources);
        });

    }
}
