package com.ars.dglue.admin.config;

import com.ars.commons.interceptor.LoginInterceptor;
import com.ars.dglue.admin.controller.interceptor.AuthInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-18.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Configuration
@AllArgsConstructor
public class WebMvcConfig extends ConfigurableWebBindingInitializer implements WebMvcConfigurer {

    private LoginInterceptor loginInterceptor;

    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login");
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login");
    }

    @Override
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
