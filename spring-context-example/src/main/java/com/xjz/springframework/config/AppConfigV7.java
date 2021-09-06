package com.xjz.springframework.config;

import com.xjz.springframework.beandefinition.BeanA;
import com.xjz.springframework.beandefinition.BeanC;
import com.xjz.springframework.importannotation.OhMyImportSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@ComponentScan({"com.xjz.springframework.beandefinition"})
@Configuration
@Import(OhMyImportSelector.class)
public class AppConfigV7 {

	@Autowired
	private BeanC beanC;

	/**
	 * @return
	 * @Bean注解的bd=ConfigutationClassBeanDefinition 默认注入模型=构造器,如果beanName相等，则并替换@Component扫描并注册到容器中的ScannedGenerocBeanDefinition
	 * @Component 注解的Bean 默认注入模型为AutowireMode.No Generic bean: class [com.xjz.springframework.beandefinition.BeanA]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0;
	 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#251行
	 */
	@Bean
	public BeanA beanA4() {
		System.out.println("实例化beanA");
		System.out.println("@Bean方法beanB = " + beanC);
		return new BeanA();
	}

}
