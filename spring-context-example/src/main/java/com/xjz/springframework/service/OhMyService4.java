package com.xjz.springframework.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Williami
 * @description
 * @date 2021/7/21
 */
@Component
public class OhMyService4 implements DisposableBean, InitializingBean {


	@PostConstruct
	public void init_Method() {
		System.out.println("执行OhMyService4初始化回调方法@PostConstruct initMethod");
	}


	public void initMethod() {
		System.out.println("执行OhMyService4初始化回调方法BeanDefinition initMethod");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("执行OhMyService4销毁回调方法");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("执行OhMyService4初始化回调方法afterPropertiesSet");
	}

}
