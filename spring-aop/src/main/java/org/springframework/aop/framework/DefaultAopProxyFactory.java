/*
 * Copyright 2002-2021 the original author or authors.
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

package org.springframework.aop.framework;

import java.io.Serializable;
import java.lang.reflect.Proxy;

import org.springframework.aop.SpringProxy;
import org.springframework.core.NativeDetector;

/**
 * Spring中AopProxyFactory唯一实现类，用来创建cglib代理或者jdk代理；
 * 默认工厂类，用来创建AopProxy
 * Default {@link AopProxyFactory} implementation, creating either a CGLIB proxy
 * or a JDK dynamic proxy.
 *
 * <p>Creates a CGLIB proxy if one the following is true for a given
 * {@link AdvisedSupport} instance:
 * <ul>
 * <li>the {@code optimize} flag is set
 * <li>the {@code proxyTargetClass} flag is set
 * <li>no proxy interfaces have been specified
 * </ul>
 *
 * <p>In general, specify {@code proxyTargetClass} to enforce a CGLIB proxy,
 * or specify one or more interfaces to use a JDK dynamic proxy.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sebastien Deleuze
 * @see AdvisedSupport#setOptimize
 * @see AdvisedSupport#setProxyTargetClass
 * @see AdvisedSupport#setInterfaces
 * @since 12.03.2004
 */
@SuppressWarnings("serial")
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {


	@Override
	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
		// 如果开启了优化，或者ProxyTargetClass设置为true
		// 或者没有提供代理类需要实现的接口，那么使用cglib代理
		// 在前面分析参数的时候已经说过了
		// 默认情况下Optimize都为false,也不建议设置为true,因为会进行一些侵入性的优化
		// 除非你对cglib的优化非常了解，否则不建议开启

		// 策略模式的典型应用场景，一般是通过环境变量、状态值、计算结果等动态地决定使用哪个策略,
		// 动态选择哪种策略的判断条件
		if (!NativeDetector.inNativeImage() &&
				(config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config))) {
			Class<?> targetClass = config.getTargetClass();
			if (targetClass == null) {
				throw new AopConfigException("TargetSource cannot determine target class: " +
						"Either an interface or a target is required for proxy creation.");
			}
			// 需要注意的是，如果需要代理的类本身就是一个接口
			// 或者需要被代理的类本身就是一个通过jdk动态代理生成的类
			// 那么不管如何设置都会使用jdk动态代理
			if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
				return new JdkDynamicAopProxy(config);
			}
			return new ObjenesisCglibAopProxy(config);
		} else {
			// 否则都是jdk动态代理
			return new JdkDynamicAopProxy(config);
		}
	}

	/**
	 * 除了SpringProxy外，判断是否有用户提供的接口
	 * Determine whether the supplied {@link AdvisedSupport} has only the
	 * {@link org.springframework.aop.SpringProxy} interface specified
	 * (or no proxy interfaces specified at all).
	 */
	private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
		Class<?>[] ifcs = config.getProxiedInterfaces();
		return (ifcs.length == 0 || (ifcs.length == 1 && SpringProxy.class.isAssignableFrom(ifcs[0])));
	}

}
