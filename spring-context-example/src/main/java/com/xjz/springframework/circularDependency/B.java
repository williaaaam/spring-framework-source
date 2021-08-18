package com.xjz.springframework.circularDependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Williami
 * @description
 * @date 2021/8/18
 */
@Component
@Scope(DefaultListableBeanFactory.SCOPE_SINGLETON)
public class B {

	/**
	 * 自己依赖自己
	 */
	@Autowired
	private A a;

	public B() {
		System.out.println("B 构造器");
	}

	/*public B() {
		System.out.println("B构造器");
	}
*/
	@PostConstruct
	public void init() {
		//com.xjz.springframework.circularDependency.A@35540566
		System.out.println("B @PostConstruct 依赖注入属性a = " + a);
	}

}
