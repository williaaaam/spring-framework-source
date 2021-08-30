package com.xjz.springframework.test;

import com.xjz.springframework.beandefinition.BeanA;
import com.xjz.springframework.circularDependency.A;
import com.xjz.springframework.circularDependency.B;
import com.xjz.springframework.config.AppConfigV3;
import com.xjz.springframework.config.AppConfigV4;
import com.xjz.springframework.config.AppConfigV6;
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

	@DisplayName("测试Bean自动注入")
	@Test
	public void autowire() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigV6.class);
		/**
		 * Generic bean: class [com.xjz.springframework.beandefinition.BeanA]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in URL [jar:file:/E:/framework/spring-framework-main/spring-context-example/build/libs/spring-context-example-5.3.9-SNAPSHOT.jar!/com/xjz/springframework/beandefinition/BeanA.class]
		 * Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfigV6; factoryMethodName=beanA3; initMethodName=null; destroyMethodName=(inferred); defined in com.xjz.springframework.config.AppConfigV6
		 */
		/*System.out.println(applicationContext.getBeanDefinition("beanA"));
		System.out.println(applicationContext.getBeanDefinition("beanA3"));*/
	}
}
