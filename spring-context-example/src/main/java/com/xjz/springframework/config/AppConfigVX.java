package com.xjz.springframework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


@ComponentScan(value = {"com.xjz.springframework.controller"}, excludeFilters = @ComponentScan.Filter(RestController.class))
@Configuration
//@EnableWebMvc
public class AppConfigVX {

	/**
	 * Spring MVC默认是不会主动注册SimpleServletHandlerAdapter,需要手动注册,但是手动注册后，会导致其余的HandlerAdapter失效
	 *
	 * @return
	 */
	@Bean
	public SimpleServletHandlerAdapter simpleServletHandlerAdapter() {
		return new SimpleServletHandlerAdapter();
	}

	@Bean
	public SimpleControllerHandlerAdapter simpleControllerHandlerAdapter() {
		return new SimpleControllerHandlerAdapter();
	}

	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		return new RequestMappingHandlerAdapter();
	}

	@Bean
	public HttpRequestHandlerAdapter httpRequestHandlerAdapter() {
		return new HttpRequestHandlerAdapter();
	}

}
