package com.xjz.springframework.aop.v2;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author william
 * @title
 * @desc
 * @date 2021/8/1
 **/
public class OhMyAroundAdvice implements MethodInterceptor {

	@Nullable
	@Override
	public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
		System.out.println("执行aroundAdvice");
		System.out.println("开始执行目标方法调用");
		return invocation.proceed();
	}
}
