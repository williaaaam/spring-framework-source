package com.xjz.springframework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@ComponentScan({"com.xjz.springframework.bc"})
@Configuration
public class AppConfigV4 {
}
