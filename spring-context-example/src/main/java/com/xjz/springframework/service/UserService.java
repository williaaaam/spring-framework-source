package com.xjz.springframework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2021/7/10
 */
@Component("xxx") // 构造器实例化
public class UserService {

	private OrderService orderService;


	/**
	 * 最终是通过Method.invoke(object,args)的方式来完成注入的，这里的method对象就是我们的setter方法:object是当前对象UserService, args = Object[1]即OrderService
	 * <p>
	 * 属性装配执行在构造器执行后
	 *
	 * @param orderService
	 * @Autowired setter转入
	 */
	@Autowired
	public void setOrderService(OrderService orderService) {
		//System.out.println("setter 注入 orderService");
		this.orderService = orderService;
	}

	public UserService() {
		//System.out.println("invoke UserService constructor");
	}

}
