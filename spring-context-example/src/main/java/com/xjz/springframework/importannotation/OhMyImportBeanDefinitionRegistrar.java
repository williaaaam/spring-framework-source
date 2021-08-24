package com.xjz.springframework.importannotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Williami
 * @description
 * @date 2021/8/24
 */
public class OhMyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Student.class);
		registry.registerBeanDefinition("student", rootBeanDefinition);
	}

}
