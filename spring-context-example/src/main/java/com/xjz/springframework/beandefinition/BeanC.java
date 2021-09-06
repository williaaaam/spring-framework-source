package com.xjz.springframework.beandefinition;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Williami
 * @description
 * @date 2021/8/30
 */
@Component
public class BeanC {

	public BeanC() {
		System.out.println("BeanC实例化");
	}

}
