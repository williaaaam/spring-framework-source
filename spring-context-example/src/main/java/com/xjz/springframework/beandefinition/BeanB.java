package com.xjz.springframework.beandefinition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Williami
 * @description
 * @date 2021/8/30
 */
@Component
public class BeanB {

	private BeanA beanA;

	// 测试属性自动注入
	public void setBeanA(BeanA beanA) {
		this.beanA = beanA;
	}

	/*public BeanB() {
	}*/

	/**
	 * 开启构造器自动注入
	 *
	 * @param beanA
	 */
	public BeanB(BeanA beanA) {
		System.out.println("BeanB 构造器注入beanA");
		this.beanA = beanA;
	}


	@PostConstruct
	public void initMethod() {
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "BeanB{" +
				"beanA=" + beanA +
				'}';
	}

}
