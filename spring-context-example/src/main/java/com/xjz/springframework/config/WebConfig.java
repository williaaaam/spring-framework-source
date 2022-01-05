package com.xjz.springframework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMVC配置
 */
@Configuration
@ComponentScan(basePackages = {"com.xjz.springframework.controller"})
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
}