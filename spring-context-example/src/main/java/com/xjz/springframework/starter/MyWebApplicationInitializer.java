package com.xjz.springframework.starter;

import com.xjz.springframework.config.RootConfig;
import com.xjz.springframework.config.WebConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @author Williami
 * @description
 * @date 2022/1/5
 */
public class MyWebApplicationInitializer extends AbstractDispatcherServletInitializer {

	/**
	 * 创建父容器
	 * @return
	 */
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		return null;
	}

	@Override
	protected WebApplicationContext createServletApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		//        applicationContext.setConfigLocation("com.git.hui.spring");
		applicationContext.register(RootConfig.class);
		applicationContext.register(WebConfig.class);
		return applicationContext;
	}


	@Override
	protected String[] getServletMappings() {
		return new String[]{"/*"};
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[]{
				new HiddenHttpMethodFilter(),
				new CharacterEncodingFilter()
		};
	}
}
