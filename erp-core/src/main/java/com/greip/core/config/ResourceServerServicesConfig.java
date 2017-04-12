package com.greip.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/*
@Configuration
@EnableAspectJAutoProxy
@ComponentScan({ "com.greip.exchange.core.service.*" })*/

@Configuration
@ComponentScan(basePackages = { "com.greip.core.service","com.greip.exchange.service"  })
public class ResourceServerServicesConfig {
    //
}
