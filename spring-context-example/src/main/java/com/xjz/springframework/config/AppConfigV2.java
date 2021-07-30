package com.xjz.springframework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration(proxyBeanMethods = true)
// 开启AOP
@EnableAspectJAutoProxy
@ComponentScan
public class AppConfigV2 {

}
