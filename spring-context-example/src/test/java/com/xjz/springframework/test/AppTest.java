package com.xjz.springframework.test;

import com.xjz.springframework.circularDependency.BarCD;
import com.xjz.springframework.circularDependency.FooCD;
import com.xjz.springframework.config.AppConfig;
import com.xjz.springframework.config.AppConfigV2;
import com.xjz.springframework.config.bean.Country;
import com.xjz.springframework.config.bean.OhMyBean;
import com.xjz.springframework.controller.OhMyController;
import com.xjz.springframework.domain.Foo;
import com.xjz.springframework.domain.Person;
import com.xjz.springframework.domain.User;
import com.xjz.springframework.factory.OhMyFactoryBean;
import com.xjz.springframework.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码参考：https://segmentfault.com/a/1190000023110150
 *
 * @author Williami
 * @description
 * @date 2021/7/10
 */
public class AppTest {

	@Test
	public void context() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		UserService bean = applicationContext.getBean(UserService.class);
		// com.xjz.springframework.service.UserService@714e0831
		System.out.println(bean);
		//Assert.assertNotNull(bean);
	}


	/**
	 * 内省机制进行属性深拷贝
	 *
	 * @see org.springframework.beans.BeanWrapperImpl#getLocalPropertyHandler(String)
	 */
	@Test
	public void registerRootBeanDefinition() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		MutablePropertyValues propertyValues = new MutablePropertyValues()
				.add("name", "XaiMing")
				.add("address", "SH")
				.add("gender", 1);
		/**
		 * Spring在启动时会实例化几个初始化的BeanDefinition,这几个BeanDefinition的类型都为RootBeanDefinition
		 * Spring在合并BeanDefinition返回的都是RootBeanDefinition
		 * 我们通过@Bean注解配置的bean，解析出来的BeanDefinition都是RootBeanDefinition（实际上是其子类ConfigurationClassBeanDefinition）
		 */
		// 1. 定义RootBeanDefinition
		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Person.class, null, propertyValues);

		// ChildBeanDefinition在实例化的时候必须要指定一个parentName
		ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition("person");
		childBeanDefinition.setBeanClass(User.class);

		// 2. 将BeanDefinition注册到Spring容器中去
		applicationContext.registerBeanDefinition("person", rootBeanDefinition);
		applicationContext.registerBeanDefinition("user", childBeanDefinition);

		applicationContext.refresh();


		// 3. 获取Bean
		User user = applicationContext.getBean(User.class);
		Person person = applicationContext.getBean(Person.class);
		System.out.println("user = " + user);
		System.out.println("person = " + person);
		System.out.println(applicationContext.getBeanDefinition("user").getSource());
		//Assert.isTrue(user.getGender() == 1);
	}

	@Test
	public void getBeanDefinition() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		BeanDefinition appConfig = applicationContext.getBeanDefinition("appConfig");
		System.out.println("appConfig BeanDefinition = " + appConfig);
		// true
		System.out.println(appConfig instanceof AnnotatedGenericBeanDefinition);

		// ConfigurationClassBeanDefinitionReader$ConfigurationClassBeanDefinition
		BeanDefinition foo = applicationContext.getBeanDefinition("foo");
		System.out.println("foo BeanDefinition = " + foo);
	}

	@DisplayName("GenericBeanDefinition代替RootBeanDefinition和ChildBeanDefinition")
	@Test
	public void genericBeanDefinition() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		MutablePropertyValues propertyValues = new MutablePropertyValues()
				// 属性名首字母可大写
				.add("Name", "XiaMing")
				.add("Gender", 1);

		/**
		 * Bean Definition Inheritance
		 * 子BeanDefinition可以继承父BeanDefinition的配置信息，还可以覆盖或者添加
		 */
		GenericBeanDefinition rootBeanDefinition = new GenericBeanDefinition();
		rootBeanDefinition.setBeanClass(Person.class);
		rootBeanDefinition.setInitMethodName("initMethod");
		rootBeanDefinition.setDestroyMethodName("destroyMethod");
		rootBeanDefinition.setPropertyValues(propertyValues);

		/**
		 * 参考:https://blog.csdn.net/qq_41907991/article/details/103866987
		 * 子BeanDefinition必须要兼容父BeanDefinition中的所有属性
		 */
		GenericBeanDefinition childBeanDefinition = new GenericBeanDefinition();
		//childBeanDefinition.setBeanClass(User.class);
		childBeanDefinition.setParentName("personal");

		applicationContext.registerBeanDefinition("personal", rootBeanDefinition);
		applicationContext.registerBeanDefinition("ohMyUser", childBeanDefinition);

		applicationContext.refresh();

		/**
		 * 子BeanDefinition会从父类继承beanClass属性
		 */
		Person user = (Person) applicationContext.getBean("ohMyUser");

		/**
		 * 会异常NoUniqueBeanDefinitionException
		 */
		//Person person = applicationContext.getBean(Person.class);·

		/**
		 * person和user不是同一个对象
		 */
		Person person = (Person) applicationContext.getBean("personal");

		System.out.println("user = " + user);
		System.out.println("person = " + person);
	}

	@DisplayName("测试AnnotatedBeanDefinition获取注解元数据和工厂方法元数据")
	@Test
	public void annotedBeanDefinition() {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

		applicationContext.register(AppConfig.class);

		applicationContext.refresh();

		//Foo bean = applicationContext.getBean(Foo.class);

		AnnotatedBeanDefinition foo = (AnnotatedBeanDefinition) applicationContext.getBeanDefinition("foo");

		System.out.println("factoryBeanName = " + foo.getFactoryBeanName()); // appConfig

		System.out.println("factoryMethodName = " + foo.getFactoryMethodName()); // foo

		System.out.println(foo.getFactoryMethodMetadata()); // public com.xjz.springframework.domain.Foo com.xjz.springframework.config.AppConfig.foo()


		Foo fooBean = applicationContext.getBean(Foo.class);
		System.out.println();

	}

	/**
	 * Xml中定义的bean对应的建模对象是GenericBeanDefinition
	 */
	@DisplayName("测试ClassPathXmlApplicationContext以及BeanDefinition合并")
	@Test
	public void mergedBeanDefinition() {

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		//BeanDefinition person = applicationContext.getBeanFactory().getBeanDefinition("person");

		// GenericBeanDefinition
		//System.out.println(applicationContext.getBean(Person.class));

		/**
		 * 打印：instance initialization method
		 * User{name='override', gender=1, title='null', address='null'}
		 */
		System.out.println(applicationContext.getBean(User.class));

		User bean = applicationContext.getBean(User.class);

		System.out.println();

	}


	@DisplayName("验证不同Bean创建方式之构造器")
	@Test
	public void noArgConstructor() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		OrderService orderService = applicationContext.getBean(OrderService.class);
		System.out.println(orderService);
	}


	@DisplayName("测试依赖注入")
	@Test
	public void di() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		applicationContext.getBean(UserService.class);
		System.out.println();
	}


	@DisplayName("单例对象依赖原型对象")
	@Test
	public void singletonDependsOnPrototype() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		/**
		 * 以下三个bean均是同一个对象
		 */
		System.out.println(applicationContext.getBean(OhMyService.class));
		System.out.println(applicationContext.getBean(OhMyService.class));
		System.out.println(applicationContext.getBean(OhMyService.class));

		/**
		 * 以下三个Bean OhMyService2是不同的对象
		 */
		System.out.println(applicationContext.getBean(OhMyService2.class));
		System.out.println(applicationContext.getBean(OhMyService2.class));
		System.out.println(applicationContext.getBean(OhMyService2.class));


		/**
		 * 开始执行单例Bean OhMyService的方法
		 * 可以看出OhMyService依赖的对象是同一个
		 */
		OhMyService bean = applicationContext.getBean(OhMyService.class);
		bean.test(1); // 1
		bean.test(2); // 3
		bean.test(3); // 6

		System.out.println("*************************************************");

		OhMyService bean2 = applicationContext.getBean(OhMyService.class);
		bean2.test(1); // 7
		bean2.test(2); // 9
		bean2.test(3); // 12

	}


	@DisplayName("Spring对象协作的几种方式")
	@Test
	public void beanCollaborator() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		applicationContext.getBean(OhMyService.class).getOhMyService2();
		applicationContext.getBean(OhMyService3.class).getOhMyService2();
	}


	/**
	 * FactoryBean要区分与factoryBean,factoryBean指静态工厂或者实例工厂创建的Bean
	 */
	@DisplayName("Spring扩展点之FactoryBean")
	@Test
	public void FactoryBean() {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		// 获取OhMyFactoryBean生产的对象
		System.out.println(applicationContext.getBean("ohMyFactoryBean")); // com.xjz.springframework.service.OrderService@36006f93
		// 查询FactoryBean对象本身itself
		System.out.println(applicationContext.getBean("&ohMyFactoryBean"));// com.xjz.springframework.factory.OhMyFactoryBean@56c8eaff

		/**
		 * 报错，OhMyFactoryBean也生产了一个Bean,此时Spring容器中有两个OrderServiceBean,Spring容器不知道该取哪一个
		 * 解决办法：在Component注解上使用@Primary注解
		 */
		System.out.println(applicationContext.getBean(OrderService.class));

		// 等同于applicationContext.getBean("&ohMyFactoryBean")
		System.out.println(applicationContext.getBean(OhMyFactoryBean.class)); // com.xjz.springframework.factory.OhMyFactoryBean@56c8eaff
	}


	@DisplayName("测试BeanPostProcessor")
	@Test
	public void BeanPostProcessor() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	}


	@DisplayName("Bean生命周期之初始化和销毁回调")
	@Test
	public void beanLifeCycle() {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		annotationConfigApplicationContext.start();
		annotationConfigApplicationContext.stop();
	}

	@DisplayName("Spring AOP核心概念")
	@Test
	public void aop() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		// OhMyController$$EnhancerBySpringCGLIB$$c078305d@3723
		OhMyController ohMyController = applicationContext.getBean(OhMyController.class);
		System.out.println("**********************************测试Spring AOP开始*********************************");
		ohMyController.eat("23333");
		System.out.println("**********************************测试Spring AOP结束*********************************");
	}


	@DisplayName("模拟解决循环依赖")
	@Test
	public void circularDependency() {
		try {
			A bean = getBean(A.class);
			System.out.println(bean.b);
			System.out.println("*************************************");
			B b = getBean(B.class);
			System.out.println(b.a);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		} finally {
		}
	}

	private final Map<String, Object> singleObjects = new HashMap<>();

	private <T> T getBean(Class<T> clz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		String beanName = clz.getSimpleName();
		if (singleObjects.containsKey(beanName.toLowerCase())) {
			return (T) singleObjects.get(beanName.toLowerCase());
		}

		// 第一步：实例化对象
		T instance = clz.newInstance();
		singleObjects.put(beanName.toLowerCase(), instance);

		// 第二步：属性注入
		Field[] declaredFields = clz.getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			Class<?> fieldClz = field.getType();
			Object bean = getBean(fieldClz);
			// 属性注入
			field.set(instance, bean);
		}

		// 第三步：返回完整的Bean
		return instance;
	}

	@DisplayName("循环依赖")
	@Test
	public void circularDependencyV2() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		System.out.println();
		System.out.println("*******************************测试循环依赖*******************************");
		//Foo bean = applicationContext.getBean(Foo.class);
		/*System.out.println(applicationContext.getBean(Foo.class));
		System.out.println(applicationContext.getBean(Foo.class));
		System.out.println(applicationContext.getBean(Foo.class));
		System.out.println(applicationContext.getBean(Foo.class));*/
		/*BarCD bean = applicationContext.getBean(BarCD.class);
		System.out.println();*/
	}

	@DisplayName("测试@Configuration")
	@Test
	public void configuration() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigV2.class);
		// com.xjz.springframework.config.AppConfig$$EnhancerBySpringCGLIB$$38ee82f3@15669693
		System.out.println(applicationContext.getBean(Country.class));
	}

}

class A {
	B b;
}

class B {
	A a;
}
