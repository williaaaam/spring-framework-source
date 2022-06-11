package com.xjz.springframework.test;

import com.xjz.springframework.config.AppConfigV4;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Williami
 * @description
 * @date 2022/3/15
 */
public class DependsOnTests {

	@Test
	public void testDependsOn() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigV4.class);
		applicationContext.start();

	}
}
