package com.xjz.springframework.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2021/7/21
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // 原型对象
public class OhMyService2 {

	int i;

	OhMyService2() {
		System.out.println("执行 OhMyService2 constructor ");
	}

	// 每次将当前对象的属性i+a然后打印
	public void addAndPrint(int a) {
		i += a;
		System.out.println(i);
	}

}
