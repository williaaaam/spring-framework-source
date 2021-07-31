package com.xjz.springframework.config;

import com.xjz.springframework.domain.Bar;
import com.xjz.springframework.domain.Foo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@Configuration(proxyBeanMethods = true)
// 开启AOP
//@EnableAspectJAutoProxy
//@ComponentScan
public class AppConfigV2 {


	@Bean
	public Foo foo() {
		Foo foo = new Foo();
		System.out.println("AppConfigV2 @Bean创建foo");
		return foo;
	}

	@Bean
	public Bar bar() {
		System.out.println("在bar方法中调用 foo = " + foo());
		return new Bar();
	}

}
