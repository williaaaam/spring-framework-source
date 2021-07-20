/*
 * Copyright 2002-2014 the original author or authors.
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

package org.springframework.beans.factory.annotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.lang.Nullable;

/**
 * 注解类型 BeanDefinition，拥有获取注解元数据和工厂方法元数据的能力
 * Extended {@link org.springframework.beans.factory.config.BeanDefinition}
 * interface that exposes {@link org.springframework.core.type.AnnotationMetadata}
 * about its bean class - without requiring the class to be loaded yet.
 *
 * @author Juergen Hoeller
 * @see AnnotatedGenericBeanDefinition
 * @see org.springframework.core.type.AnnotationMetadata
 * @since 2.5
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

	/**
	 * 获取注解元数据
	 * Obtain the annotation metadata (as well as basic class metadata)
	 * for this bean definition's bean class.
	 *
	 * @return the annotation metadata object (never {@code null})
	 */
	AnnotationMetadata getMetadata();

	/**
	 * 这个方法跟我们的@Bean注解相关。当我们在一个配置类中使用了@Bean注解时，被@Bean注解标记的方法，就被解析成了FactoryMethodMetadata
	 * Obtain metadata for this bean definition's factory method, if any.
	 *
	 * @return the factory method metadata, or {@code null} if none
	 * @since 4.1.1
	 */
	@Nullable
	MethodMetadata getFactoryMethodMetadata();

}
