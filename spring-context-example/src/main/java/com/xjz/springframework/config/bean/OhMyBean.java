package com.xjz.springframework.config.bean;

import com.xjz.springframework.domain.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2021/7/30
 */
//@Component
public class OhMyBean {

	@Autowired
	private Country countryV3;

	@Bean
	public Country country() {
		System.out.println("2 " + countryV3);
		return new Country();
	}

	@Bean
	public Foo foo() {
		System.out.println("1 " + countryV3);
		return new Foo();
	}

}
