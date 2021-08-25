package com.xjz.springframework.importannotation;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Objects;

/**
 * @author Williami
 * @description
 * @date 2021/8/24
 */
public class OhMyDeferredImportSelector implements DeferredImportSelector {

	/**
	 * 需要导入的组件的全类名数组
	 *
	 * @param importingClassMetadata StandardAnnotationMetadata  AnnotationMetadata是Import注解所在的类属性（如果所在类是注解类，则延伸至应用这个注解类的非注解类为止）
	 * @return
	 */
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		//这里的importingClassMetadata针对的是使用@EnableService的非注解类
		//因为`AnnotationMetadata`是`Import`注解所在的类属性，如果所在类是注解类，则延伸至应用这个注解类的非注解类为止
		Map<String, Object> map = importingClassMetadata.getAnnotationAttributes(EnableAppConfigV6.class.getName(), true);
		String name = (String) map.get("name");
		if (Objects.equals(name, "AppConfigV6")) {
			return new String[]{"com.xjz.springframework.importannotation.Student"};
		}
		return new String[0];
	}

}
