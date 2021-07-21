package com.xjz.springframework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2021/7/15
 */
@Component
public class OrderService {

	/**
	 * 默认使用BarService无参构造函数
	 * Field.set(OrderService,BarService)方式完成注入
	 */
	@Autowired
	BarService barService;

	/*public OrderService() {
		System.out.println("invoke OrderService Constructor");
	}
	*/

	/**
	 * 1. 可以混用构造器注入和setter 依赖注入
	 * 1.1 构造器注入适用于强制依赖 官方推荐 ！
	 * 1.2 setter注入适用于可选注入
	 * @param barService
	 */
	public OrderService(BarService barService) {
		// JavaBean实例化时进行了一次注入
		this.barService = barService;
		System.out.println("invoke barService Constructor");
		System.out.println("cons " + barService);// 完成了实例化
	}

	@Autowired
	public void setBarService(BarService barService) {
		this.barService = null;
		System.out.println("setter 注入 null barService"); // setter注入时覆盖实例化时的注入
		System.out.println("setter参数"+barService);
		System.out.println("setter " + this.barService);
	}


}
