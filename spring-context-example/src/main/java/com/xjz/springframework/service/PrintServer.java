package com.xjz.springframework.service;

import org.springframework.stereotype.Component;

@Component
public class PrintServer {

	public void print() {
		System.out.println(System.currentTimeMillis());
	}

}