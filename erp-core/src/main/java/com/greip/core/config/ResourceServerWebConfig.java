package com.greip.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.greip.core.controller","com.greip.exchange.controller" })
public class ResourceServerWebConfig extends WebMvcConfigurerAdapter {
//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver createMultipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("utf-8");
//        return resolver;
//    }


}
