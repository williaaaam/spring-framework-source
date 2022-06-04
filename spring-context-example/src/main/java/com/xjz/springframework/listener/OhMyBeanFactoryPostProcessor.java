package com.xjz.springframework.listener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author william
 * @title
 * @desc
 * @date 2022/6/4
 **/
@Component
public class OhMyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	public OhMyBeanFactoryPostProcessor() {
		System.out.println("OhMyBeanFactoryPostProcessor 实例化");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("OhMyBeanFactoryPostProcessor#postProcessBeanFactory");
	}

}
