package com.xjz.springframework.config.ohnyproxy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author william
 * @title
 * @desc
 * @date 2022/4/30
 **/
public class ConfigTests {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ProxyBeanMethodConfig.class);
		ProxyBeanMethodConfig config = annotationConfigApplicationContext.getBean(ProxyBeanMethodConfig.class);
		ProxyBeanMethodConfig.Bar bar = annotationConfigApplicationContext.getBean(ProxyBeanMethodConfig.Bar.class);
		System.out.println();
	}
}
