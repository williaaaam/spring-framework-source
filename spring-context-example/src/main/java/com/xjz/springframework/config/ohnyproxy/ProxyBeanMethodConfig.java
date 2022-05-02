package com.xjz.springframework.config.ohnyproxy;

import com.xjz.springframework.domain.Bar;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


//@Configuration(proxyBeanMethods = true) // 不会创建ProxyBeanMethodConfig CGLIB代理对象，Foo构造器被调用两次
@Configuration(proxyBeanMethods = false) // 不会创建ProxyBeanMethodConfig CGLIB代理对象，Foo构造器被调用两次
public class ProxyBeanMethodConfig {

	public ProxyBeanMethodConfig() {
		System.out.println("");
	}

	// factoryBean 就是CGLIB生成的代理对象
	//	Object result = factoryMethod.invoke(factoryBean, args);
	@Bean
	public Bar bar() {
		//foo();
		return new Bar();
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_PROTOTYPE)
	public Foo foo() {
		return new Foo();
	}


	public class Bar {

		private Foo foo;

		public Foo getFoo() {
			return foo;
		}

		public void setFoo(Foo foo) {
			this.foo = foo;
		}

		@Override
		@SuppressWarnings("deprecation")
		protected void finalize() throws Throwable {
			try {
				System.out.println("Invoke Bar finalize");
			} finally {
				super.finalize();
			}
		}
	}

	public class Foo {

		@Override
		@SuppressWarnings("deprecation")
		protected void finalize() throws Throwable {
			try {
				System.out.println("Invoke Foo finalize");
			} finally {
				super.finalize();
			}
		}

		public Foo() {
			System.out.println("Foo构造器");
		}

	}

}
