package com.xjz.springframework.config;

import com.xjz.springframework.importannotation.OhMyImportSelector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@ComponentScan({"com.xjz.springframework.domain"})
@Configuration
public class AppConfigV6 {
}
