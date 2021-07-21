package com.xjz.springframework.service;

import org.springframework.stereotype.Service;

/**
 * @author Williami
 * @description
 * @date 2021/7/21
 */
@Service
public class BarService {

	public BarService(){}

	public BarService(String name) {
		System.out.println("invoke BarService 1 arg constructor");
	}
}
