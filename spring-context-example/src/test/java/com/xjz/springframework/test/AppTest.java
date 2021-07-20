package com.xjz.springframework.test;

import com.xjz.springframework.config.AppConfig;
import com.xjz.springframework.domain.Foo;
import com.xjz.springframework.domain.Person;
import com.xjz.springframework.domain.User;
import com.xjz.springframework.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
}
