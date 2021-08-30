package com.xjz.springframework.importannotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * SpringBoot自动装配原理
 *
 * @author Williami
 * @description
 * @date 2021/8/24
 */
public class OhMyImportSelector implements ImportSelector {

	/**
	 * ConfigurationClassParser 609行执行该方法；需要导入的组件的全类名数组
	 *
	 * @param importingClassMetadata StandardAnnotationMetadata  AnnotationMetadata是Import注解所在的类属性（如果所在类是注解类，则延伸至应用这个注解类的非注解类为止）
	 * @return
	 */
	/*@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		// 可以是@Configuration注解修饰的类，也可以是具体的Bean类的全限定名称
		//return new String[]{"com.xjz.springframework.importannotation.Student"};
		return new String[]{"com.xjz.springframework.config.AppConfigV4"};
	}*/
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		List<String> candidateConfigurations = getCandidateConfigurations();
		return StringUtils.toStringArray(candidateConfigurations);
	}

	protected List<String> getCandidateConfigurations() {
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
				OhMyImportSelector.class.getClassLoader());
		Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
				+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}

	/**
	 * Return the class used by {@link SpringFactoriesLoader} to load configuration
	 * candidates.
	 *
	 * @return the factory class
	 */
	protected Class<?> getSpringFactoriesLoaderFactoryClass() {
		return EnableAppConfigV6.class;
	}

}
