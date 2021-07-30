/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.scope;

import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Utility class for creating a scoped proxy.
 *
 * <p>Used by ScopedProxyBeanDefinitionDecorator and ClassPathBeanDefinitionScanner.
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Sam Brannen
 * @since 2.5
 */
public abstract class ScopedProxyUtils {

	private static final String TARGET_NAME_PREFIX = "scopedTarget.";

	private static final int TARGET_NAME_PREFIX_LENGTH = TARGET_NAME_PREFIX.length();


	/**
	 * 当我们选择在@Scope注解中配置了proxyMode属性时（INTERFACES/TARGET_CLASS），那么Spring会在注册bd时，在容器中注册一个代理的bd，这个bd是一个ScopedProxyFactoryBean类型的bd，并且没有特别指定它的作用域，所以它是单例的，并且这个FactoryBean返回就是对应的目标对象的代理对象。基于此，Spring就可以利用这个bd来完成在启动阶段对session/request域对象的注入
	 * 原文链接：https://blog.csdn.net/qq_41907991/article/details/105667900
	 * // 根据目标对象的bd生成代理对象的bd,并且会将目标对象的bd注册到容器中
	 * Generate a scoped proxy for the supplied target bean, registering the target
	 * bean with an internal name and setting 'targetBeanName' on the scoped proxy.
	 *
	 * @param definition       the original bean definition
	 * @param registry         the bean definition registry
	 * @param proxyTargetClass whether to create a target class proxy
	 * @return the scoped proxy definition
	 * @see #getTargetBeanName(String)
	 * @see #getOriginalBeanName(String)
	 */
	public static BeanDefinitionHolder createScopedProxy(BeanDefinitionHolder definition,
														 BeanDefinitionRegistry registry, boolean proxyTargetClass) {

		// 原始对象的名称
		String originalBeanName = definition.getBeanName();
		// 目标对象的bd
		BeanDefinition targetDefinition = definition.getBeanDefinition();
		// 将来会将目标对象的bd注册到容器中，targetBeanName作为注册时的key
		// targetBeanName = "scopedTarget."+originalBeanName
		String targetBeanName = getTargetBeanName(originalBeanName);

		// Create a scoped proxy definition for the original bean name,
		// "hiding" the target bean in an internal target definition.
		// 创建代理对象的bd,代理对象是FactoryBean,FactoryBean用来创建交给Spring容器的Bean
		RootBeanDefinition proxyDefinition = new RootBeanDefinition(ScopedProxyFactoryBean.class);
		// 代理对象所装饰的bd就是目标对象的bd
		// 拷贝了部分目标对象bd中的属性到代理对象的bd中
		proxyDefinition.setDecoratedDefinition(new BeanDefinitionHolder(targetDefinition, targetBeanName));
		proxyDefinition.setOriginatingBeanDefinition(targetDefinition);
		proxyDefinition.setSource(definition.getSource());
		proxyDefinition.setRole(targetDefinition.getRole());

		proxyDefinition.getPropertyValues().add("targetBeanName", targetBeanName);
		if (proxyTargetClass) {
			// cglib代理
			targetDefinition.setAttribute(AutoProxyUtils.PRESERVE_TARGET_CLASS_ATTRIBUTE, Boolean.TRUE);
			// ScopedProxyFactoryBean's "proxyTargetClass" default is TRUE, so we don't need to set it explicitly here.
		} else {
			// jdk动态代理
			proxyDefinition.getPropertyValues().add("proxyTargetClass", Boolean.FALSE);
		}

		// Copy autowire settings from original bean definition.
		// 拷贝目标对象自动注入配置
		proxyDefinition.setAutowireCandidate(targetDefinition.isAutowireCandidate());
		proxyDefinition.setPrimary(targetDefinition.isPrimary());
		if (targetDefinition instanceof AbstractBeanDefinition) {
			proxyDefinition.copyQualifiersFrom((AbstractBeanDefinition) targetDefinition);
		}

		// The target bean should be ignored in favor of the scoped proxy.
		// 将目标对象Bean从自动注入中排除，优先使用代理对象
		targetDefinition.setAutowireCandidate(false);
		targetDefinition.setPrimary(false);

		// Register the target bean as separate bean in the factory.
		// 这一步会将原始的bd注册到容器中，其中的key="scopedTarget."+originalBeanName
		registry.registerBeanDefinition(targetBeanName, targetDefinition);

		// Return the scoped proxy definition as primary bean definition
		// (potentially an inner bean).
		// 优先返回代理对象bd
		return new BeanDefinitionHolder(proxyDefinition, originalBeanName, definition.getAliases());
	}

	/**
	 * Generate the bean name that is used within the scoped proxy to reference the target bean.
	 *
	 * @param originalBeanName the original name of bean
	 * @return the generated bean to be used to reference the target bean
	 * @see #getOriginalBeanName(String)
	 */
	public static String getTargetBeanName(String originalBeanName) {
		return TARGET_NAME_PREFIX + originalBeanName;
	}

	/**
	 * Get the original bean name for the provided {@linkplain #getTargetBeanName
	 * target bean name}.
	 *
	 * @param targetBeanName the target bean name for the scoped proxy
	 * @return the original bean name
	 * @throws IllegalArgumentException if the supplied bean name does not refer
	 *                                  to the target of a scoped proxy
	 * @see #getTargetBeanName(String)
	 * @see #isScopedTarget(String)
	 * @since 5.1.10
	 */
	public static String getOriginalBeanName(@Nullable String targetBeanName) {
		Assert.isTrue(isScopedTarget(targetBeanName), () -> "bean name '" +
				targetBeanName + "' does not refer to the target of a scoped proxy");
		return targetBeanName.substring(TARGET_NAME_PREFIX_LENGTH);
	}

	/**
	 * Determine if the {@code beanName} is the name of a bean that references
	 * the target bean within a scoped proxy.
	 *
	 * @since 4.1.4
	 */
	public static boolean isScopedTarget(@Nullable String beanName) {
		return (beanName != null && beanName.startsWith(TARGET_NAME_PREFIX));
	}

}
