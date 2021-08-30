package com.xjz.springframework.config;

import com.xjz.springframework.beandefinition.BeanA;
import com.xjz.springframework.importannotation.OhMyImportSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@ComponentScan({"com.xjz.springframework.beandefinition"})
@Configuration
@Import(OhMyImportSelector.class)
public class AppConfigV6 {

	/**
	 * @return
	 * @Bean注解的bd=ConfigutationClassBeanDefinition 默认注入模型=构造器,如果beanName相等，则并替换@Component扫描并注册到容器中的ScannedGenerocBeanDefinition
	 * @Component 注解的Bean 默认注入模型为AutowireMode.No Generic bean: class [com.xjz.springframework.beandefinition.BeanA]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0;
	 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#251行
	 */
	@Bean
	public BeanA beanA3() {
		System.out.println("*******************************************************************");
		System.out.println("实例化@Bean BeanA前");
		BeanA beanA = new BeanA();
		System.out.println("实例化@Bean BeanA" + beanA);
		System.out.println("*******************************************************************");
		return beanA;
	}

}
