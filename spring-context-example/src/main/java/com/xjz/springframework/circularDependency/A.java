package com.xjz.springframework.circularDependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Williami
 * @description
 * @date 2021/8/18
 */
@Component
@Scope(DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@DependsOn("B") // DependsOn只是保证被依赖的bean先于当前bean被实例化，被创建
public class A {

	/**
	 * 自己依赖自己
	 */
	@Autowired
	private B a;

	/*public A(B b){
	}*/

	public A(){
		System.out.println("A 构造器");
	}


	@PostConstruct
	public void init() {
		//com.xjz.springframework.circularDependency.A@35540566
		//System.out.println("A @PostConstruct 依赖注入属性b = " + a);
		System.out.println("A初始化");
	}

}
