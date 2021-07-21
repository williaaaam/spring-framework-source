package com.xjz.springframework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2021/7/21
 */
@Component
public class OhMyService {

	@Autowired
	OhMyService2 ohMyService;


	OhMyService3 ohMyService3;

	public void test(int a) {
		/**
		 * 依赖注入阶段已经完成了ohMyService注入，在调用test方法时不会再进行注入，所以我们使用的是同一个对象,这种情况下原型对象失去了意义
		 */
		System.out.println(this.ohMyService);
		// setter注入
		System.out.println(this.ohMyService3);
		ohMyService.addAndPrint(a);
	}

	//@Autowired 为了测试OhMyService3懒初始化暂时将注解注释掉
	public void setOhMyService3(OhMyService3 ohMyService3) {
		this.ohMyService3 = ohMyService3;
	}

}
