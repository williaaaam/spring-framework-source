package com.xjz.springframework.config;

import com.xjz.springframework.domain.Foo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author Williami
 * @description
 * @date 2021/7/10
 */
@ComponentScan("com.xjz.springframework")
// @Configuration注解的Bean会被解析成AnnotatedGenericBeanDefinition
/**
 * @see org.springframework.context.annotation.AnnotatedBeanDefinitionReader#doRegisterBean(Class, String, Class[], Supplier, BeanDefinitionCustomizer[])  方法中使用AnnotatedGenericBeanDefinition对象来承载beanClass
 * @see org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition
 *
 * <p>
 * @Configuration也是通过无参构造器创建的Bean实例
 */
@Configuration
public class AppConfig {

	/**
	 * @return
	 * @Bean注解的Bean 会被解析成ConfigurationClassBeanDefinition {@link org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader.ConfigurationClassBeanDefinition}
	 */
	@Bean // 把对象交给Spring容器管理
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public Foo foo() {
		return new Foo();
	}

}
