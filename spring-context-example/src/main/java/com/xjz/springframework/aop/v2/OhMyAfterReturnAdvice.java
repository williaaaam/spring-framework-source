package com.xjz.springframework.aop.v2;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author william
 * @title
 * @desc
 * @date 2021/8/1
 **/
public class OhMyAfterReturnAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("*****************开始执行afterReturningAdvice**************8");
		System.out.println("returnValue= " + returnValue);
		System.out.println("method= " + method.getName());
		System.out.println("target= " + target);
		System.out.println("*****************结束执行afterReturningAdvice**************8");
	}
}
