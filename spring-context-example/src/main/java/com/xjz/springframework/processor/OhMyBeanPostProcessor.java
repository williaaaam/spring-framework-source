package com.xjz.springframework.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Williami
 * @description
 * @date 2021/7/22
 */
@Component
public class OhMyBeanPostProcessor implements BeanPostProcessor {

	/**
	 * 实现beanPostProcessor接口的两个方法均在Bean构造器之后执行
	 * @param bean the new bean instance
	 * @param beanName the name of the bean
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if ("orderService".equals(beanName)) {
			System.out.println("触发执行OrderService postProcessBeforeInitialization, bean= " + bean);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if ("orderService".equals(beanName)) {
			System.out.println("触发执行OrderService postProcessAfterInitialization,bean = " + bean);
		}
		return bean;
	}

}
