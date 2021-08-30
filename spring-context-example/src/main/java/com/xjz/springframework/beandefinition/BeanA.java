package com.xjz.springframework.beandefinition;

import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2021/8/30
 */
@Component
public class BeanA {

	public BeanA() {
		System.out.println("BeanA 实例化" + this);
	}

}
