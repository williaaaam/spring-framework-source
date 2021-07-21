package com.xjz.springframework.service;

/**
 * @author Williami
 * @description
 * @date 2021/7/21
 */
public class OhMyFactory {

	private static final Object OBJECT = new Object();


	/**
	 * 静态工厂方法对应的FactoryBeanName对应的类不需要实例化
	 *
	 * @return
	 */
	public static Object staticMethod() {
		return OBJECT;
	}

	/**
	 * 实例工厂方法对应的Bean需要实例化
	 *
	 * @return
	 */
	public Object method() {
		return OBJECT;
	}

}
