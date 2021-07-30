package com.xjz.springframework.config.bean;

import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2021/7/30
 */
@Component("countryV3")
public class Country {

	public Country() {
		System.out.println("执行Country无参无参构造器" + this);
	}
}
