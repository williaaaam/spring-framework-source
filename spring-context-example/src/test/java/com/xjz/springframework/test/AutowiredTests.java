package com.xjz.springframework.test;

import com.xjz.springframework.config.AppConfigV8;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Williami
 * @description
 * @date 2021/9/7
 */
public class AutowiredTests {


	@Test
	public void autowiredTest() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigV8.class);
		applicationContext.registerShutdownHook();
	}
}
