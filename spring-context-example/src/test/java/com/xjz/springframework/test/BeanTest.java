package com.xjz.springframework.test;

import com.xjz.springframework.circularDependency.A;
import com.xjz.springframework.circularDependency.B;
import com.xjz.springframework.config.AppConfigV3;
import com.xjz.springframework.config.AppConfigV4;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Williami
 * @description
 * @date 2021/8/18
 */
public class BeanTest {


	@DisplayName("测试循环依赖之原型和单例")
	@Test
	public void cd() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigV3.class);
		System.out.println("********************************");
		System.out.println(applicationContext.getBean(A.class));
		System.out.println(applicationContext.getBean(B.class));
	}

	@DisplayName("测试Bean不同包Bean加载顺序")
	@Test
	public void loadBean() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigV4.class);
	}
}
