package com.xjz.springframework.test;

import com.xjz.springframework.config.AppConfig;
import com.xjz.springframework.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
		Assert.assertNotNull(bean);
	}
}
