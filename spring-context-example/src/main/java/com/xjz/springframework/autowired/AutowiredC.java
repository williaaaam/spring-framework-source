package com.xjz.springframework.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Williami
 * @description
 * @date 2021/9/7
 */
@Component
public class AutowiredC {


	/*@Autowired
	@Qualifier("autowiredA")
	IAutowired autowired;*/

	@Autowired
	private IAutowired autowiredA;

	private IAutowired autowiredB;


	/*AutowiredC() {
		System.out.println("执行了AutowiredC无参构造器");
	}

	public AutowiredC(IAutowired autowiredA) {
		this.autowiredA = autowiredA;
		System.out.println("执行了AutowiredC(#)构造器");
	}*/

	/*public AutowiredC(IAutowired autowiredA, IAutowired autowiredB) {
		this.autowiredA = autowiredA;
		this.autowiredB = autowiredB;
		System.out.println("执行了AutowiredC(#,#)构造器");
	}*/

	@Autowired
	public void setAutowiredB(String nane) {
		//this.autowiredB = autowiredB;
	}

	@PostConstruct
	public void init() {
		System.out.println("autowiredA = " + autowiredA);
		System.out.println("autowiredB = " + autowiredB);
	}

	@PreDestroy
	public void destroy() {
		System.out.println("AutowiredC destroy...");
	}

}
