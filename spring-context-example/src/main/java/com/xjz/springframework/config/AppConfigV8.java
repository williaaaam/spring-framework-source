package com.xjz.springframework.config;

import com.xjz.springframework.beandefinition.BeanA;
import com.xjz.springframework.beandefinition.BeanC;
import com.xjz.springframework.importannotation.OhMyImportSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@ComponentScan({"com.xjz.springframework.autowired"})
@Configuration
public class AppConfigV8 {

}
