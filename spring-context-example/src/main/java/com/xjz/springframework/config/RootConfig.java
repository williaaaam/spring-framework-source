package com.xjz.springframework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 */
@Configuration
@ComponentScan(value = "com.xjz.springframework.service", excludeFilters = @ComponentScan.Filter(RestController.class))
public class RootConfig {

}