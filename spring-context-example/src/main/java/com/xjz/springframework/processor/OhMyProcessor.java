package com.xjz.springframework.processor;

import com.xjz.springframework.service.OrderService;
import com.xjz.springframework.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Williami
 * @description
 * @date 2021/7/15
 */
@Component
public class OhMyProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		//Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(System.out::println);
		GenericBeanDefinition orderServiceBeanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("orderService");
		/**
		 * 实际上生成了UserService Bean,启动时如果也扫描了UserService，则报错：expected single matching bean but found 2: orderService,userService
		 */
		orderServiceBeanDefinition.setBeanClass(UserService.class);
	}
}
