package com.xjz.springframework.circularDependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2021/7/27
 */
@Component
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FooCD {

	FooCD() {
		System.out.println("FooCD 无参构造器");
	}

	//@Autowired
	BarCD barCD;

	FooCD(BarCD barCD) {
		this.barCD = barCD;
		System.out.println("FooCD 有参构造器");
	}

}
