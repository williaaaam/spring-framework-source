package com.xjz.springframework.importannotation;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Spring整合Mybatis,Spring Cloud Feign
 * @author Williami
 * @description
 * @date 2021/8/24
 */
public class OhMyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	/*@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Student.class);
		registry.registerBeanDefinition("student", rootBeanDefinition);

		// 重新定义Bean
		//BeanDefinition student = registry.getBeanDefinition("student");

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Student.class)
				.addConstructorArgValue("Michael");

		// Student被注册了两次：No qualifying bean of type 'com.xjz.springframework.importannotation.Student' available: expected single matching bean but found 2: student,student2
		registry.registerBeanDefinition("student2", beanDefinitionBuilder.getBeanDefinition());

	}*/

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		// 重新定义Bean,例如动态注入属性、改变Bean的类型和Scope等
		//BeanDefinition student = registry.getBeanDefinition("student");

		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Student.class)
				.addConstructorArgValue("Michael");

		registry.registerBeanDefinition("student2", beanDefinitionBuilder.getBeanDefinition());

	}

}
