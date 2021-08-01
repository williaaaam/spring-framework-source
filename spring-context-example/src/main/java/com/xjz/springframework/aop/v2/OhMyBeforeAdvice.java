package com.xjz.springframework.aop.v2;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author william
 * @title
 * @desc
 * @date 2021/8/1
 **/
public class OhMyBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("*****************开始执行methodBeforeAdvice**************8");
		System.out.println("method= " + method.getName());
		System.out.println("arget= " + target);
		System.out.println("*****************结束执行methodBeforeAdvice**************8");
	}

}
