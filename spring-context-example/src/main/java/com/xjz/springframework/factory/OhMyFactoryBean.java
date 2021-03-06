package com.xjz.springframework.factory;

import com.xjz.springframework.service.OrderService;
import com.xjz.springframework.service.UserService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Williami
 * @description
 * @date 2021/7/22
 */
@Component
public class OhMyFactoryBean implements FactoryBean<OrderService> {

	@Autowired
	UserService userService;

	// 把对象交给Spring容器管理
	// 无法解决expected single matching bean but found 2: ohMyFactoryBean,orderService
	//@Primary
	@Override
	public OrderService getObject() throws Exception {
		System.out.println("OhMyFactoryBean创建OrderService"+" userService "+userService);
		OrderService orderService = new OrderService();
		System.out.println(orderService);
		return orderService;
	}

	@Override
	public Class<?> getObjectType() {
		return OrderService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
