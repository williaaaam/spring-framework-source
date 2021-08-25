package com.xjz.springframework.config;

import com.xjz.springframework.importannotation.EnableAppConfigV6;
import com.xjz.springframework.importannotation.OhMyImportBeanDefinitionRegistrar;
import com.xjz.springframework.importannotation.OhMyImportSelector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


//@ComponentScan({"com.xjz.springframework.importannotation"})
@Configuration
// Import注解将Java对象注册到Spring容器中
//@Import(Student.class)
@Import(OhMyImportSelector.class)
//@Import({OhMyImportSelector.class, AppConfigV4.class})
//@EnableAppConfigV6(name = "AppConfigV6")
//@Import(OhMyImportBeanDefinitionRegistrar.class)
public class AppConfigV5 {
}
