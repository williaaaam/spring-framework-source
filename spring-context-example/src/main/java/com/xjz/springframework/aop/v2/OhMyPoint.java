package com.xjz.springframework.aop.v2;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;

/**
 * @author william
 * @title
 * @desc
 * @date 2021/8/1
 **/
public class OhMyPoint implements Pointcut {

	@Override
	public ClassFilter getClassFilter() {
		// 在类级别上不进行拦截
		return ClassFilter.TRUE;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return new StaticMethodMatcherPointcut() {
			@Override
			public boolean matches(@NonNull Method method, Class<?> targetClass) {
				// 对于toString方法不进行拦截
				return !method.getName().equals("toString");
			}
		};
	}
}
