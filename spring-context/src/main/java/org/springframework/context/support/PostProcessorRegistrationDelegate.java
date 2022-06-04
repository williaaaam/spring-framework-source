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

package org.springframework.context.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.StartupStep;
import org.springframework.lang.Nullable;

/**
 * Delegate for AbstractApplicationContext's post-processor handling.
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @since 4.0
 */
final class PostProcessorRegistrationDelegate {

	private PostProcessorRegistrationDelegate() {
	}


	/**
	 * 1. 先执行直接实现了BeanDefinitionRegistryPostProcessor接口的后置处理器，所有实现了BeanDefinitionRegistryPostProcessor接口的类有两个方法，一个是特有的postProcessBeanDefinitionRegistry方法，一个是继承子父接口的postProcessBeanFactory方法。
	 * postProcessBeanDefinitionRegistry方法早于postProcessBeanFactory方法执行，
	 * 对于postProcessBeanDefinitionRegistry的执行顺序又遵循如下原则
	 * 1.1 先执行实现了PriorityOrdered接口的类中的postProcessBeanDefinitionRegistry方法
	 * 1.2 再执行实现了Ordered接口的类中的postProcessBeanDefinitionRegistry的方法
	 * 1.3 最后执行没有实现上面两个接口的类中的postProcessBeanDefinitionRegistry的方法
	 * 1.4 执行完所有的postProcessBeanDefinitionRegistry方法后，再执行实现了BeanDefinitionRegistryPostProcessor接口的类中的postProcessBeanFactory方法
	 * 2. 再执行直接实现了BeanFactoryPostProcessor接口的后置处理器
	 * 2.1 先执行实现了PriorityOrdered接口的类中的postProcessBeanFactory方法
	 * 2.2 再执行实现了Ordered接口的类中的postProcessBeanFactory的方法
	 * 2.3 最后执行没有实现上面两个接口的类中的postProcessBeanFactory的方法
	 * ————————————————
	 * 原文链接：https://blog.csdn.net/qq_41907991/article/details/103867027
	 * 每次调用beanFactory.getBeanNamesForType都进行了一次bd的合并
	 *
	 * @param beanFactory
	 * @param beanFactoryPostProcessors
	 */
	public static void invokeBeanFactoryPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {

		// WARNING: Although it may appear that the body of this method can be easily
		// refactored to avoid the use of multiple loops and multiple lists, the use
		// of multiple lists and multiple passes over the names of processors is
		// intentional. We must ensure that we honor the contracts for PriorityOrdered
		// and Ordered processors. Specifically, we must NOT cause processors to be
		// instantiated (via getBean() invocations) or registered in the ApplicationContext
		// in the wrong order.
		//
		// Before submitting a pull request (PR) to change this method, please review the
		// list of all declined PRs involving changes to PostProcessorRegistrationDelegate
		// to ensure that your proposal does not result in a breaking change:
		// https://github.com/spring-projects/spring-framework/issues?q=PostProcessorRegistrationDelegate+is%3Aclosed+label%3A%22status%3A+declined%22

		// Invoke BeanDefinitionRegistryPostProcessors first, if any.
		// 保存BeanDefinitionRegistryPostProcessor名
		Set<String> processedBeans = new HashSet<>();

		// 这个if基本上一定会成立，除非我们手动new了一个beanFactory
		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			// 存储了只实现了BeanFactoryPostProcessor接口的后置处理器
			List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
			// 存储了实现了BeanDefinitionRegistryPostProcessor接口并执行postProcessBeanDefinitionRegistry方法的后置处理器
			List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();

			// 这个beanFactoryPostProcessors集合一般情况下都是空的，除非我们手动调用容器的addBeanFactoryPostProcessor方法
			for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
				if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
					BeanDefinitionRegistryPostProcessor registryProcessor = (BeanDefinitionRegistryPostProcessor) postProcessor;
					/**
					 * 执行实现了BeanDefinitionRegistryPostProcessor接口的后置处理器的postProcessBeanDefinitionRegistry方法，
					 * 注意这里执行的不是postProcessBeanFactory方法，我们上面已经讲过了，实现了BeanDefinitionRegistryPostProcessor接口的后置处理器有两个方法，
					 * 一个是从父接口中继承而来的postProcessBeanFactory方法，另一个是这个接口特有的postProcessBeanDefinitionRegistry方法
					 * 原文链接：https://blog.csdn.net/qq_41907991/article/details/103867027
					 */
					registryProcessor.postProcessBeanDefinitionRegistry(registry);
					// 主要用来执行程序员通过手动调用API注册的容器的后置处理器
					/**
					 * // 保存执行过了的BeanDefinitionRegistryPostProcessor，这里执行过的BeanDefinitionRegistryPostProcessor只是代表它的特有方法：postProcessBeanDefinitionRegistry方法执行过了，
					 * 但是千万记得，它还有一个标准的postProcessBeanFactory，也就是从父接口中继承的方法还未执行
					 * 原文链接：https://blog.csdn.net/qq_41907991/article/details/103867027
					 */
					registryProcessors.add(registryProcessor);
				} else {
					// 将只实现了BeanFactoryPostProcessor接口的后置处理器加入到集合中
					regularPostProcessors.add(postProcessor);
				}
			}

			// Do not initialize FactoryBeans here: We need to leave all regular beans
			// uninitialized to let the bean factory post-processors apply to them!
			// Separate between BeanDefinitionRegistryPostProcessors that implement
			// PriorityOrdered, Ordered, and the rest.
			/**
			 * 	保存当前需要执行的实现了BeanDefinitionRegistryPostProcessor接口的后置处理器
			 */
			List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();

			// First, invoke the BeanDefinitionRegistryPostProcessors that implement PriorityOrdered.
			// 在BeanDefinitionMap已经存在的ConfigurationClassPostProcessor
			// 发生一次bd的合并
			// 这里只会获取实现了BeanDefinitionRegistryPostProcessor接口的Bean的名字
			/**
			 * 		从容器中获取到所有实现了BeanDefinitionRegistryPostProcessor接口的Bean的名字
			 */
			String[] postProcessorNames =
					beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				// 筛选实现了PriorityOrdered接口的后置处理器
				if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
					/**
					 *                // 如果满足条件，会将其创建出来，同时添加到集合中
					 *                 // 正常情况下，只会有一个，就是Spring容器自己提供的ConfigurationClassPostProcessor,Spring通过这个类完成了扫描以及BeanDefinition的功能
					 */
					// 对于注解启动的Spring容器来说，是把key = org.springframework.context.annotation.internalConfigurationAnnotationProcessor对应的ConfigurationClassPostProcessor处理器加入到currentRegistryProcessors中
					// 创建ConfigurationCLassPostProcessor Bean单例
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					// 去重
					processedBeans.add(ppName);
				}
			}

			// 根据实现的PriorityOrdered接口进行排序
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			/**
			 *   将当前将要执行的currentRegistryProcessors全部添加到registryProcessors这个集合中,currentRegistryProcessors中的两个方法均为执行
			 */
			registryProcessors.addAll(currentRegistryProcessors);
			// 只存在一个internalConfigurationAnnotationProcessor 处理器，用于扫描:
			// 这里只会执行了实现了PriorityOrdered和BeanDefinitionRegistryPostProcessor的后置处理器ConfigurationClassPostProcessor
			/**
			 *   执行后置处理器的逻辑，这里只会执行BeanDefinitionRegistryPostProcessor接口的postProcessBeanDefinitionRegistry方法,currentRegistryProcessors中父类方法还未执行
			 */
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry, beanFactory.getApplicationStartup());
			currentRegistryProcessors.clear();

			// Next, invoke the BeanDefinitionRegistryPostProcessors that implement Ordered.
			// 这里又进行了一个bd的合并
			/**
			 *       // 这里重新获取实现了BeanDefinitionRegistryPostProcesso接口的后置处理器的名字，思考一个问题：为什么之前获取了一次不能直接用呢？还需要获取一次呢？
			 *       这是因为，在我们上面执行过了BeanDefinitionRegistryPostProcessor中，可以在某个类中，我们扩展的时候又注册了一个实现了BeanDefinitionRegistryPostProcessor接口的后置处理器
			 */
			postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				// 确保没有被处理过并且实现了Ordered接口
				if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
					// 加入到当前需要被执行的集合中
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					processedBeans.add(ppName);
				}
			}

			// 根据ordered接口进行排序
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			// 将当前将要执行的currentRegistryProcessors全部添加到registryProcessors这个集合中
			registryProcessors.addAll(currentRegistryProcessors);
			// 执行后置处理器的逻辑，这里只会执行BeanDefinitionRegistryPostProcessor接口的postProcessBeanDefinitionRegistry方法
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry, beanFactory.getApplicationStartup());
			currentRegistryProcessors.clear();

			// 接下来这段代码是为了确认所有实现了BeanDefinitionRegistryPostProcessor的后置处理器能够执行完，之所有要一个循环中执行，也是为了防止在执行过程中注册了新的BeanDefinitionRegistryPostProcessor
			// Finally, invoke all other BeanDefinitionRegistryPostProcessors until no further ones appear.
			boolean reiterate = true;
			while (reiterate) {
				reiterate = false;
				// 这里再次进行了一次bd的合并
				// 获取普通的BeanDefinitionRegistryPostProcessor，【不需要实现PriorityOrdered或者Ordered接口，这个注释可能不对？】
				postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
				for (String ppName : postProcessorNames) {
					if (!processedBeans.contains(ppName)) {
						// 筛选只实现了BeanDefinitionRegistryPostProcessor的后置处理器
						// 只要发现有一个需要执行了的后置处理器，就需要再次循环，因为执行了这个后置处理可能会注册新的BeanDefinitionRegistryPostProcessor
						currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
						processedBeans.add(ppName);
						reiterate = true;
					}
				}
				sortPostProcessors(currentRegistryProcessors, beanFactory);
				registryProcessors.addAll(currentRegistryProcessors);
				// 执行的是普通的后置处理器，即没有实现任何排序接口（PriorityOrdered或Ordered)
				invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry, beanFactory.getApplicationStartup());
				currentRegistryProcessors.clear();
			}

			// Now, invoke the postProcessBeanFactory callback of all processors handled so far.
			// 这里开始执行单独实现了BeanFactoryPostProcessor接口的后置处理器
			// 1.先执行实现了BeanDefinitionRegistryPostProcessor的BeanFactoryPostProcessor，在前面的逻辑中我们只执行了BeanDefinitionRegistryPostProcessor特有的postProcessBeanDefinitionRegistry方法，它的postProcessBeanFactory方法还没有被执行，它会在这里被执行
			invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
			// 2.执行直接实现了BeanFactoryPostProcessor接口的后置处理器
			invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
		} else {
			// Invoke factory processors registered with the context instance.
			// 正常情况下，进不来这个判断，不用考虑
			invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
		}

		// Do not initialize FactoryBeans here: We need to leave all regular beans
		// uninitialized to let the bean factory post-processors apply to them!
		// 获取所有实现了BeanFactoryPostProcessor接口的后置处理器，这里会获取到已经执行过的后置处理器，所以后面的代码会区分已经执行过或者未执行过
		String[] postProcessorNames =
				beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);

		// Separate between BeanFactoryPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		/**
		 * // 保存直接实现了BeanFactoryPostProcessor接口和PriorityOrdered接口的后置处理器
		 */
		List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();

		/**
		 * 保存直接实现了BeanFactoryPostProcessor接口和Ordered接口的后置处理器
		 */
		List<String> orderedPostProcessorNames = new ArrayList<>();

		// 保存直接实现了BeanFactoryPostProcessor接口的后置处理器，不包括那些实现了排序接口的类
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();
		for (String ppName : postProcessorNames) {
			if (processedBeans.contains(ppName)) {
				// 已经处理过了直接跳出
				// skip - already processed in first phase above
			} else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				// 添加实现PriorityOrdered接口和BeanFactoryPostProcessor接口的后置工厂处理器
				priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
			} else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			} else {
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
		// 先执行实现了BeanFactoryPostProcessor接口和PriorityOrdered接口的后置处理器
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);

		// Next, invoke the BeanFactoryPostProcessors that implement Ordered.
		// 紧接着执行实现了Ordered接口的BeanFactoryPostProcessors
		List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
		for (String postProcessorName : orderedPostProcessorNames) {
			orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		sortPostProcessors(orderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);

		// Finally, invoke all other BeanFactoryPostProcessors.
		List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
		for (String postProcessorName : nonOrderedPostProcessorNames) {
			nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);

		// Clear cached merged bean definitions since the post-processors might have
		// modified the original metadata, e.g. replacing placeholders in values...
		// 清空合并的BeanDefinition
		beanFactory.clearMetadataCache();
	}

	public static void registerBeanPostProcessors(
			ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {

		// WARNING: Although it may appear that the body of this method can be easily
		// refactored to avoid the use of multiple loops and multiple lists, the use
		// of multiple lists and multiple passes over the names of processors is
		// intentional. We must ensure that we honor the contracts for PriorityOrdered
		// and Ordered processors. Specifically, we must NOT cause processors to be
		// instantiated (via getBean() invocations) or registered in the ApplicationContext
		// in the wrong order.
		//
		// Before submitting a pull request (PR) to change this method, please review the
		// list of all declined PRs involving changes to PostProcessorRegistrationDelegate
		// to ensure that your proposal does not result in a breaking change:
		// https://github.com/spring-projects/spring-framework/issues?q=PostProcessorRegistrationDelegate+is%3Aclosed+label%3A%22status%3A+declined%22

		// 1.获取容器中已经注册的Bean的名称，根据BeanDefinition中获取BeanName
		/**
		 * 此时这些后置处理器还未被创建出来
		 */
		String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);

		// Register BeanPostProcessorChecker that logs an info message when
		// a bean is created during BeanPostProcessor instantiation, i.e. when
		// a bean is not eligible for getting processed by all BeanPostProcessors.
		// 2.通过addBeanPostProcessor方法添加的BeanPostProcessor以及已经注册到容器中的BeanPostProcessor的总数量
		/**
		 * 这里主要是获取容器中已经存在的BeanPostProcessor的数量再加上已经被扫描出BeanDefinition的后置处理器的数量（后置处理器还没有被创建出来）
		 */
		int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;

		// 3.添加一个BeanPostProcessorChecker，主要用于日志记录
		beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));

		// Separate between BeanPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		// 保存同时实现了BeanPostProcessor跟PriorityOrdered接口的后置处理器
		List<BeanPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
		// 保存实现了MergedBeanDefinitionPostProcessor接口的后置处理器
		List<BeanPostProcessor> internalPostProcessors = new ArrayList<>();
		// 保存同时实现了BeanPostProcessor跟Ordered接口的后置处理器的名字
		List<String> orderedPostProcessorNames = new ArrayList<>();
		// 保存同时实现了BeanPostProcessor但没有实现任何排序接口的后置处理器的名字
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();

		// 4.遍历所有的后置处理器的名字，并根据不同类型将其放入到上面申明的不同集合中
		// 同时会将实现了PriorityOrdered接口的后置处理器创建出来
		// 如果实现了MergedBeanDefinitionPostProcessor接口，放入到internalPostProcessors
		for (String ppName : postProcessorNames) {
			if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				// 创建BeanPostProcessor
				/**
				 * CommonAnnotationBeanPostProcessor后置处理器实例化时，设置初始化方法注解@PostConstruct和销毁初始化方法注解@PreDestroy
				 */
				BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
				priorityOrderedPostProcessors.add(pp);
				if (pp instanceof MergedBeanDefinitionPostProcessor) {
					internalPostProcessors.add(pp);
				}
			} else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			} else {
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, register the BeanPostProcessors that implement PriorityOrdered.
		// 5.将priorityOrderedPostProcessors集合排序
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		// 6.将priorityOrderedPostProcessors集合中的后置处理器添加到容器中
		registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);

		// Next, register the BeanPostProcessors that implement Ordered.
		List<BeanPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
		for (String ppName : orderedPostProcessorNames) {
			// 创建实现了Ordered接口的BeanPostProcessor
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			orderedPostProcessors.add(pp);
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}

		// 按照Ordered接口排序
		sortPostProcessors(orderedPostProcessors, beanFactory);
		registerBeanPostProcessors(beanFactory, orderedPostProcessors);

		// Now, register all regular BeanPostProcessors.

		// 7.遍历所有实现了常规后置处理器（没有实现任何排序接口）的名字，并进行创建
		// 如果实现了MergedBeanDefinitionPostProcessor接口，放入到internalPostProcessors
		List<BeanPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
		for (String ppName : nonOrderedPostProcessorNames) {
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			nonOrderedPostProcessors.add(pp);
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}
		registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);

		// Finally, re-register all internal BeanPostProcessors.
		sortPostProcessors(internalPostProcessors, beanFactory);
		registerBeanPostProcessors(beanFactory, internalPostProcessors);

		// Re-register post-processor for detecting inner beans as ApplicationListeners,
		// moving it to the end of the processor chain (for picking up proxies etc).
		// 10.最后添加的这个后置处理器主要为了可以检测到所有的事件监听器
		beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
	}

	private static void sortPostProcessors(List<?> postProcessors, ConfigurableListableBeanFactory beanFactory) {
		// Nothing to sort?
		if (postProcessors.size() <= 1) {
			return;
		}
		Comparator<Object> comparatorToUse = null;
		if (beanFactory instanceof DefaultListableBeanFactory) {
			comparatorToUse = ((DefaultListableBeanFactory) beanFactory).getDependencyComparator();
		}
		if (comparatorToUse == null) {
			comparatorToUse = OrderComparator.INSTANCE;
		}
		postProcessors.sort(comparatorToUse);
	}

	/**
	 * Invoke the given BeanDefinitionRegistryPostProcessor beans.
	 */
	private static void invokeBeanDefinitionRegistryPostProcessors(
			Collection<? extends BeanDefinitionRegistryPostProcessor> postProcessors, BeanDefinitionRegistry registry, ApplicationStartup applicationStartup) {

		for (BeanDefinitionRegistryPostProcessor postProcessor : postProcessors) {
			StartupStep postProcessBeanDefRegistry = applicationStartup.start("spring.context.beandef-registry.post-process")
					.tag("postProcessor", postProcessor::toString);
			postProcessor.postProcessBeanDefinitionRegistry(registry);
			postProcessBeanDefRegistry.end();
		}
	}

	/**
	 * Invoke the given BeanFactoryPostProcessor beans.
	 */
	private static void invokeBeanFactoryPostProcessors(
			Collection<? extends BeanFactoryPostProcessor> postProcessors, ConfigurableListableBeanFactory beanFactory) {

		for (BeanFactoryPostProcessor postProcessor : postProcessors) {
			StartupStep postProcessBeanFactory = beanFactory.getApplicationStartup().start("spring.context.bean-factory.post-process")
					.tag("postProcessor", postProcessor::toString);
			postProcessor.postProcessBeanFactory(beanFactory);
			postProcessBeanFactory.end();
		}
	}

	/**
	 * Register the given BeanPostProcessor beans.
	 */
	private static void registerBeanPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanPostProcessor> postProcessors) {

		if (beanFactory instanceof AbstractBeanFactory) {
			// Bulk addition is more efficient against our CopyOnWriteArrayList there
			((AbstractBeanFactory) beanFactory).addBeanPostProcessors(postProcessors);
		} else {
			for (BeanPostProcessor postProcessor : postProcessors) {
				beanFactory.addBeanPostProcessor(postProcessor);
			}
		}
	}


	/**
	 * BeanPostProcessor that logs an info message when a bean is created during
	 * BeanPostProcessor instantiation, i.e. when a bean is not eligible for
	 * getting processed by all BeanPostProcessors.
	 */
	private static final class BeanPostProcessorChecker implements BeanPostProcessor {

		private static final Log logger = LogFactory.getLog(BeanPostProcessorChecker.class);

		private final ConfigurableListableBeanFactory beanFactory;

		private final int beanPostProcessorTargetCount;

		public BeanPostProcessorChecker(ConfigurableListableBeanFactory beanFactory, int beanPostProcessorTargetCount) {
			this.beanFactory = beanFactory;
			this.beanPostProcessorTargetCount = beanPostProcessorTargetCount;
		}

		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) {
			return bean;
		}

		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) {
			if (!(bean instanceof BeanPostProcessor) && !isInfrastructureBean(beanName) &&
					this.beanFactory.getBeanPostProcessorCount() < this.beanPostProcessorTargetCount) {
				if (logger.isInfoEnabled()) {
					logger.info("Bean '" + beanName + "' of type [" + bean.getClass().getName() +
							"] is not eligible for getting processed by all BeanPostProcessors " +
							"(for example: not eligible for auto-proxying)");
				}
			}
			return bean;
		}

		private boolean isInfrastructureBean(@Nullable String beanName) {
			if (beanName != null && this.beanFactory.containsBeanDefinition(beanName)) {
				BeanDefinition bd = this.beanFactory.getBeanDefinition(beanName);
				return (bd.getRole() == RootBeanDefinition.ROLE_INFRASTRUCTURE);
			}
			return false;
		}
	}

}
