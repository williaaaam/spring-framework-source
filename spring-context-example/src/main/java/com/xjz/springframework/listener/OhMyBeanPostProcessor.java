package com.xjz.springframework.listener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author william
 * @title
 * @desc
 * @date 2022/6/4
 **/
@Component
public class OhMyBeanPostProcessor implements BeanPostProcessor {

	public OhMyBeanPostProcessor() {
		System.out.println("OhMyBeanPostProcessor 实例化");
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("OhMyBeanPostProcessor#postProcessBeforeInitialization >>> "+ beanName);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("OhMyBeanPostProcessor#postProcessAfterInitialization >>> "+beanName);
		return bean;
	}
}
