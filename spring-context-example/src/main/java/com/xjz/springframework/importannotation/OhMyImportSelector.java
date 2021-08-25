package com.xjz.springframework.importannotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
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
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		// 可以是@Configuration注解修饰的类，也可以是具体的Bean类的全限定名称
		//return new String[]{"com.xjz.springframework.importannotation.Student"};
		return new String[]{"com.xjz.springframework.config.AppConfigV4"};
	}

}
