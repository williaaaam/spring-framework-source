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

//@Component
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BarCD {
	BarCD() {
		//System.out.println("Bar 无参构造器");
	}

	//@Autowired
	private FooCD fooCD;

	public BarCD(FooCD fooCD) {
		this.fooCD = fooCD;
		System.out.println("Bar 有参构造器");
	}

	public FooCD getFooCD() {
		return fooCD;
	}
}
