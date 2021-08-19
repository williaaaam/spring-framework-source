package com.xjz.springframework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan({"com.xjz.springframework.bc", "com.xjz.springframework.a"})
@Configuration
public class AppConfigV4 {
}
