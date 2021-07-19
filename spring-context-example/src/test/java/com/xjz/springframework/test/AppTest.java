package com.xjz.springframework.test;

import com.xjz.springframework.config.AppConfig;
import com.xjz.springframework.domain.User;
import com.xjz.springframework.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * @author Williami
 * @description
 * @date 2021/7/10
 */
public class AppTest {

	@Test
	public void context() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		UserService bean = applicationContext.getBean(UserService.class);
		// com.xjz.springframework.service.UserService@714e0831
		System.out.println(bean);
		//Assert.assertNotNull(bean);
	}


	@Test
	public void registerRootBeanDefinition() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		MutablePropertyValues propertyValues = new MutablePropertyValues()
				.add("name", "XaiMing")
				.add("gender", 1);

		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(User.class, null, propertyValues);
		applicationContext.registerBeanDefinition("user", rootBeanDefinition);
		applicationContext.refresh();

		User user = applicationContext.getBean(User.class);
		Assert.isTrue(user.getGender() == 1);
	}
}
