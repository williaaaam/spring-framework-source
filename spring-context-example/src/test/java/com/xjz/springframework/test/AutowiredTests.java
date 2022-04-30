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

	@Test
	public void testParentAndChild() {

		Parent parent = new Child();
		// 运行时错误
		Child c = (Child) new Parent();
		c.m();

	}


	public static class Parent {

	}

	public static class Child extends Parent {
		public void m(){

		}
	}
}
